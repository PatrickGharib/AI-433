import DataClass.Lab
import DataClass.PSol
import DataClass.Section
import IO.ParsedData
import Tree.Constr
import Tree.Eval

class CourseSchedulerTree(root: PSol) : AndTree<PSol>(root) {

    val courseLookupTable = arrayOf(ParsedData.COURSES)


    override fun childGen(pred: PSol): List<PSol> {
        val c = pred.slotLookup(null).firstOrNull()
        val x = mutableListOf<PSol>()
        if (c is Lab) ParsedData.LAB_SLOT.forEach {
            if (it != null) {

                val p = pred.assign(c, it)
                //TODO not sure if Cosntr() is correct
                if (Constr().constrPartial(p) && p.courseLookup(c) != null) {
                    x.add(p)


                }
            }
        }
        if (c is Section) ParsedData.COURSE_SLOTS.forEach {
            if (it != null) {

                val p = pred.assign(c, it)
                //TODO not sure if Cosntr() is correct
                if (Constr().constrPartial(p) && p.courseLookup(c) != null) {
                    x.add(p)
                }
            }
        }


        return x
    }
}