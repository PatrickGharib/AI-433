import DataClass.PSol
import IO.ParsedData
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class CourseSchedulerProcess(root: PSol): SearchProcess<CourseSchedulerTree, PSol>() {
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

        val duration = TimeUnit.MINUTES.toMillis(5)

        // atomic in case we wanted to thread it
        val count = AtomicInteger(0)
        val skipped = AtomicInteger(0)

        // find initial candidate
        while (candidate== null && model.peekDeepest() !=null && (System.currentTimeMillis()-start) < duration){
            count.incrementAndGet()

            // do work
            fTrans(fLeafDepth())
        }



        // deallocate depth first queue.
        model.depthFirst.clear()

        // search for anything better.
        while (model.peekBest() != null && (System.currentTimeMillis()-start) < TimeUnit.MINUTES.toMillis(5)){

            // skip any bad nodes.
            while (model.peekBest()?.data?.value ?: 1000001 >= candidate?.value ?: 1000000) {
                model.best()
                skipped.incrementAndGet()
                count.incrementAndGet()
                if (model.peekBest() == null) {
                    break
                }
            }
            // quit if we run out of nodes.
            if (model.peekBest() == null) break
            count.incrementAndGet()

            // do work
            fTrans(fLeafBest())
        }
        println("Examined $count leaves, skipping $skipped. This means we skipped ${(skipped.get().toFloat()/count.get().toFloat())*100}%.")
        return candidate
    }

    private fun fLeafDepth(): AndTree<PSol>.Node? {
        return model.deepest()
    }

    private fun fLeafBest(): AndTree<PSol>.Node? {
        return model.best()
    }

    private fun fTrans(node: AndTree<PSol>.Node?) {

        node!!.expand()

        // candidate?.value ?: 100000 explanation:
        // candidate?.value says IF candidate != null then get its value, otherwise return null, (no null pointer exception)
        // then ?: says if the left is null then return the right, in this case 100000.
        // so if candidate is null it goes: (candidate?.value) ?: 100000 -> (null) ?: 100000 -> 100000

        // examine the current node.
        node.solved = node.data.complete
        if (node.solved && node.data.complete && node.data.value < (candidate?.value ?: 1000000000)) {
            candidate = node.data
            model.depthmode = false
            println(candidate?.value.toString()+ "||||" + candidate?.slotLookup(null) + "||" +candidate?.courseSet()?.count()+"/"+(ParsedData.COURSES.count()+ParsedData.LABS.count()))
        }

        // examine the children
        node.children.forEach {
            it.solved = it.data.complete
            if (it.solved  && (it.data.value < (candidate?.value ?: 1000000000))) {
                candidate = it.data
                model.depthmode = false
                println(candidate?.value.toString()+ "||||" + candidate?.slotLookup(null) + "||" +candidate?.courseSet()?.filter { candidate?.courseLookup(it) != null }?.count()+"/"+(ParsedData.COURSES.count()+ParsedData.LABS.count()))

            }
        }
    }

    override val model: CourseSchedulerTree = CourseSchedulerTree(this,root)

}