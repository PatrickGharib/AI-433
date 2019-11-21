import java.lang.Exception

abstract class AndTree<T>(root: T) {

    private val _leaves = mutableListOf(Node(root))

    val leaves: List<Node> get() = _leaves

    abstract fun childGen(pred: T) : List<T>

    inner class Node(val value: T,private val _children: MutableList<Node> = mutableListOf()){
        val children: List<Node> get() = _children

        fun expand(){
            if (_children.isEmpty()) {
                childGen(value).forEach {
                    val x = Node(it)
                    _children.add(x)
                    _leaves.add(x)
                }
            }else{
                throw Exception("Non-empty children")
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AndTree<*>.Node

            if (value != other.value) return false
            if (_children != other._children) return false

            return true
        }

        override fun hashCode(): Int {
            var result = value?.hashCode() ?: 0
            result = 31 * result + _children.hashCode()
            return result
        }
    }

}