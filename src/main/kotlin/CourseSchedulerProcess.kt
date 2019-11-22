import DataClass.PSol

class CourseSchedulerProcess(root: PSol): SearchProcess<CourseSchedulerTree, PSol>() {

    override fun fLeaf(leaves: List<AndTree<PSol>.Node>): AndTree<PSol>.Node {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fTrans(node: AndTree<PSol>.Node) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val model: CourseSchedulerTree = CourseSchedulerTree(root)

}