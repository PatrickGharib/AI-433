import DataClass.PSol

class CourseSchedulerTree(root: PSol) : AndTree<PSol>(root)  {
    override fun childGen(pred: PSol): List<PSol> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun best(): PSol? {
        return leaves.minBy { Eval().eval(it.value, ParsedData.PAIR) }?.value
    }
}