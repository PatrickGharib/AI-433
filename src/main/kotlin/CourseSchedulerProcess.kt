import DataClass.PSol

class CourseSchedulerProcess(root: PSol): SearchProcess<CourseSchedulerTree, PSol>() {



    override fun fLeaf(leaves: List<AndTree<PSol>.Node>): AndTree<PSol>.Node? {
        return model.best()
    }

    override fun fTrans(node: AndTree<PSol>.Node?) {
        node!!.expand()
        if (node.children.isEmpty()){
            node.solved = true
        }

        // candidate?.value ?: 100000 explanation:
        // candidate?.value says IF candidate != null then get its value, otherwise return null, (no null pointer exception)
        // then ?: says if the left is null then return the right, in this case 100000.
        // so if candidate is null it goes: (candidate?.value) ?: 100000 -> (null) ?: 100000 -> 100000

        if (node.solved && node.data.complete && node.data.value < (candidate?.value ?: 100000)){
            candidate = node.data
        }
        node.children.forEach {
            it.solved = Solved(it)
            if (it.solved && it.data.complete && (it.data.value < (candidate?.value ?: 100000))){
                candidate = it.data
            }
        }
    }

    private fun Solved(it: AndTree<PSol>.Node): Boolean {
        //only need to determine if solution is complete
        return it.data.complete
    }

    override val model: CourseSchedulerTree = CourseSchedulerTree(root)

}