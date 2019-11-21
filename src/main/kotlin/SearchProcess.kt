abstract class SearchProcess<T: AndTree<J>, J> {

    abstract val model: T

    abstract fun fLeaf(leaves: List<AndTree<J>.Node>) : AndTree<J>.Node
    abstract fun fTrans(node: AndTree<J>.Node)

    abstract var candidate: J

    fun execute() : J{
        while (model.leaves.any{ it -> !it.solved }){
            fTrans(fLeaf(model.leaves))
        }
        return candidate
    }


}