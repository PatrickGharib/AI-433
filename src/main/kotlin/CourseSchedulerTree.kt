import DataClass.PSol
import IO.ParsedData
import Tree.Constr
import Tree.Eval

class CourseSchedulerTree(root: PSol) : AndTree<PSol>(root) {

    val courseLookupTable = arrayOf(ParsedData.COURSES)


    override fun childGen(pred: PSol): List<PSol> {
        val c = pred.slotLookup(null).firstOrNull()
        val x = mutableListOf<PSol>()
        if (c != null) {
            ((ParsedData.COURSE_SLOTS) + (ParsedData.LAB_SLOT)).forEach {
                if (it != null && it !in pred.slotSet()) {

                    val p = pred.assign(c, it)
                    //TODO not sure if Cosntr() is correct
                    if (Constr().constrPartial(p)) {
                        x.add(p)


                    }
                }
            }
        }
            return x
    }
}