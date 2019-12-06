import DataClass.PSol
import IO.ParsedData
import kotlinx.coroutines.*

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.Thread.sleep
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

class CourseSchedulerProcess(root: PSol, private val duration_m: Long = 5,val num_threads: Int = 4): SearchProcess<CourseSchedulerTree, PSol>() {

    val mutex  = Mutex()
    val distQueue = ConcurrentLinkedQueue<AndTree<PSol>.Node>()
    override fun execute(): PSol? {
        // start time

        val start = System.currentTimeMillis()


        // ?: means return left, unless its null then return right instead ( "?:" is known as the Elvis operator in kotlin (turn your screen))
        // putting ? in method call chains says if this thing is null, that's fine, evaluate to null.
        // together this makes this happen:
        // return model.peekBest()?.data?.value ?: 1000000
        // is the same as:
        // val x = model.peekBest()
        // if ( x == null || x.data == null){
        //      return 1000000
        // }else{
        //      return x.data.value
        // }

        val duration = TimeUnit.MINUTES.toMillis(duration_m)

        // atomic in case we wanted to thread it
        val count = AtomicInteger(0)
        val skipped = AtomicInteger(0)


        // find initial candidate
        /*
        while (candidate== null && model.peekDeepest() !=null && (System.currentTimeMillis()-start) < duration){
            count.incrementAndGet()

            // do work
            fTrans(fLeafDepth())
        }
        */
        // deallocate depth first queue.



        val queuePool = mutableListOf<PriorityBlockingQueue<AndTree<PSol>.Node>>()
        val done = AtomicBoolean(false)


        for (i in 1..num_threads){
            queuePool.add(PriorityBlockingQueue(5))
        }

        // worker threads
        val jobs = List(num_threads){
            thread{
                //println(it)
                val queue = queuePool[it]
                while (!done.get()) {

                    while (queue.peek() == null && !done.get()) {
                        //sleep(1)
                        if (done.get()) break
                    }
                    if (done.get()) break
                    count.incrementAndGet()
                    // do work
                    runBlocking {
                        fTrans(queue.poll())
                    }
                }
                //println("$it done")
            }
        }

        //jobs.forEach { it.start() }

        var current = 0

        val stats = mutableListOf<Int>()
        for (i in 0..num_threads){
            stats.add(0)
        }

        distQueue.add(model.best())
        // Manager thread

        runBlocking {
            launch{
                while((System.currentTimeMillis() - start) < duration){
                    //println("running")
                    while (distQueue.peek() == null && (System.currentTimeMillis() - start) < duration){
                        //println("waiting ${(System.currentTimeMillis() - start)} $duration")
                        if ((System.currentTimeMillis() - start) >= duration){
                            //println("qutting")
                            done.set(true)
                            break
                        }
                    }
                    if (done.get() || distQueue.peek() == null) break
                    val x = distQueue.poll()
                    if (x.data.value >= candidate?.value ?: 1000000) continue
                    queuePool[current].add(x)
                    current = (current+1) % (num_threads)
                    stats[current]++
                }
                //println("Quit")
                done.set(true)
            }
        }

        //println(stats)

        //wait for threads to catch up
        runBlocking {
            jobs.forEach { it.join() }
        }
        println("Examined $count leaves, skipping $skipped. This means we skipped ${(skipped.get().toFloat()/count.get().toFloat())*100}%.")
        return candidate
    }



    private fun fLeafBest(): AndTree<PSol>.Node? {
        return model.best()
    }

    private suspend fun asyncUpdate(sol: PSol){
        mutex.withLock {
            if (candidate?.value ?: 1000000 > sol.value && sol.slotLookup(null).isEmpty()) candidate = sol
        }
    }

    private suspend fun fTrans(node: AndTree<PSol>.Node?) {
        if (node == null) return
        node.expand(distQueue)
        //println(node.depth)
        //println(node.data.value)

        // candidate?.value ?: 100000 explanation:
        // candidate?.value says IF candidate != null then get its value, otherwise return null, (no null pointer exception)
        // then ?: says if the left is null then return the right, in this case 100000.
        // so if candidate is null it goes: (candidate?.value) ?: 100000 -> (null) ?: 100000 -> 100000


        // examine the current node.
        node.solved = node.data.complete
        if (node.solved && node.data.complete && (node.data.slotLookup(null).isEmpty()) && node.data.value < (candidate?.value ?: 1000000000)) {
            coroutineScope {
                launch{
                    asyncUpdate(node.data)
                }
            }
            if (candidate != null) {
                model.depthmode = false
                println(candidate?.value.toString() + "||||" + candidate?.slotLookup(null) + "||" + candidate?.courseSet()?.count() + "/" + (ParsedData.COURSES.count() + ParsedData.LABS.count()))
            }
        }

        // examine the children
        //if (node.children.isEmpty()) println("Dead End")
        node.children.forEach {
            it.solved = it.data.complete
            if (it.solved  && (it.data.slotLookup(null).isEmpty()) && (it.data.value < (candidate?.value ?: 1000000000))) {
                coroutineScope {
                    launch{
                        asyncUpdate(node.data)
                    }
                }
                model.depthmode = false
                if (candidate != null) {
                    println(candidate?.value.toString() + "||||" + candidate?.slotLookup(null) + "||" + candidate?.courseSet()?.filter { candidate?.courseLookup(it) != null }?.count() + "/" + (ParsedData.COURSES.count() + ParsedData.LABS.count()))
                }

            }
        }
    }

    override val model: CourseSchedulerTree = CourseSchedulerTree(this,root)

}
