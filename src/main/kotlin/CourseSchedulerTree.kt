import DataClass.Lab
import DataClass.PSol
import DataClass.Section
import IO.ParsedData
import Tree.Constr
import Tree.Eval
import java.util.*

class CourseSchedulerTree(val process :CourseSchedulerProcess, root: PSol) : AndTree<PSol>(root) {


    override fun childGen(pred: PSol): List<PSol> {
        val c = pred.slotLookup(null).firstOrNull()
        val x = mutableListOf<PSol>()
        if (c is Lab) ParsedData.LAB_SLOT.forEach {
            if (it != null) {

                val p = pred.assign(c, it)

                // throw out children worse or equal to our current best. Drastically speeds up the algorithm.
                // Technically not our model, but since we know the nodes will never be examined anyways according our model, there is no point in wasting the memory.
                // goes from a ~89% skip rate to a ~2%, without slowing down the algorithm.

                // essentially these are going to be skipped anyways, may as well skip them now instead of wasting time examining them later.
                if (Constr.getInstance(ParsedData.NOT_COMPATIBLE,ParsedData.UNWANTED).constrPartial(p) && p.courseLookup(c) != null && (process.candidate?.value ?: 1000000) > p.value) {
                    x.add(p)
                }
            }
        }
        if (c is Section) ParsedData.COURSE_SLOTS.forEach {
            if (it != null) {

                val p = pred.assign(c, it)

                //TODO not sure if Cosntr() is correct
                if (Constr.getInstance(ParsedData.NOT_COMPATIBLE,ParsedData.UNWANTED).constrPartial(p) && p.courseLookup(c) != null  && (process.candidate?.value ?: 1000000) > p.value) {
                    x.add(p)
                }
            }
        }


        return x
    }
}