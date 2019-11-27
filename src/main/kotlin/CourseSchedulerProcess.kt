import DataClass.PSol
import IO.ParsedData
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer
import kotlin.time.TimedValue
import kotlin.time.measureTime

class CourseSchedulerProcess(root: PSol): SearchProcess<CourseSchedulerTree, PSol>() {
    override fun execute(): PSol? {
        val start = System.currentTimeMillis()
        // ?: means return left, unelss its null then return right instead ( "?:" is known as the Elvis operator in kotlin (turn your screen))
        // putting ? in method clal chains says if this thing is null, thats fine, evaluate to null.
        // together this makes this happen:
        // return model.peekBest()?.data?.value ?: 1000000
        // is the same as:
        // val x = model.peekBest()
        // if ( x == null || x.data == null){
        //      return 1000000
        // }else{
        //      return x.data.value
        // }
        while ((model.peekBest()?.data?.value ?: 1000000) < (candidate?.value ?: 1000000) && (System.currentTimeMillis()-start) < TimeUnit.MINUTES.toMillis(15)){

            fTrans(fLeaf(model.leaves))

        }
        return candidate
    }


    override fun fLeaf(leaves: List<AndTree<PSol>.Node>): AndTree<PSol>.Node? {
        return model.best()
    }

    override fun fTrans(node: AndTree<PSol>.Node?) {
        node!!.expand()
        if (node.children.isEmpty()) {
            node.solved = true
            println("solved!")
        }

        // candidate?.value ?: 100000 explanation:
        // candidate?.value says IF candidate != null then get its value, otherwise return null, (no null pointer exception)
        // then ?: says if the left is null then return the right, in this case 100000.
        // so if candidate is null it goes: (candidate?.value) ?: 100000 -> (null) ?: 100000 -> 100000

        if (node.solved && node.data.complete && node.data.value < (candidate?.value ?: 100000)) {
            candidate = node.data
            //println("New Candidate!")
        }

        node.children.forEach {
            it.solved = Solved(it)
            if (it.solved && it.data.complete && (it.data.value < (candidate?.value ?: 100000))) {
                candidate = it.data
            }
            //println("Examined child!")
        }
        println(candidate?.value.toString()+ "||" + model.leaves.count() + "||" + candidate?.slotLookup(null) + "||" +candidate?.courseSet()?.count()+"/"+(ParsedData.COURSES.count()+ParsedData.LABS.count()))
    }

    private fun Solved(it: AndTree<PSol>.Node): Boolean {
        //only need to determine if solution is complete
        return it.data.complete
    }

    override val model: CourseSchedulerTree = CourseSchedulerTree(root)

}