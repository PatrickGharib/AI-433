import DataClass.PSol

class CourseSchedulerTree(root: PSol) : AndTree<PSol>(root)  {
    override fun childGen(pred: PSol): List<PSol> {
        val c = pred.slotLookup(null).firstOrNull()
        val x = mutableListOf<PSol>()
        if (c != null) {
            pred.slotSet().forEach {
                if (it == null) return@forEach
                val p = pred.assign(c,it)
                if (Constr().constr(p)){
                    x.add(p)
                }
            }
        }
        return x
    }

    override fun best(): PSol? {
        return leaves.minBy { Eval( 1,1,1,1,ParsedData.PAIR).eval(it.value) }?.value
    }
}