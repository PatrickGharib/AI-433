import DataClass.PSol

abstract class SearchProcess<T: AndTree<J>, J : Comparable<J>> {

    abstract val model: T

    var candidate: J? = null

    abstract fun execute() : J?


}
