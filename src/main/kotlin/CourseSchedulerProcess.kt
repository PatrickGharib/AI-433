import DataClass.PSol

class CourseSchedulerProcess(root: PSol): SearchProcess<CourseSchedulerTree, PSol>() {



    override fun fLeaf(leaves: List<AndTree<PSol>.Node>): AndTree<PSol>.Node? {
        return model.best()
    }

    override fun fTrans(node: AndTree<PSol>.Node?) {
        node!!.expand()
        if (node.children.isEmpty()){
            node.solved = true
            return
        }
        node.children.forEach {
            it.solved = Solved(it)
            if (it.solved &&  (it.data.value > candidate?.value ?: 100000)){
                candidate = it.data
            }
        }
    }

    private fun Solved(it: AndTree<PSol>.Node): Boolean {
        //only need to determine if soloution is complete
        return it.data.complete
    }

    override val model: CourseSchedulerTree = CourseSchedulerTree(root)

}