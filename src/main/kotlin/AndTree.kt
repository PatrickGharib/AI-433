import DataClass.HeapArrayQueue
import DataClass.PriorityQueue
import java.lang.Exception

abstract class AndTree<T: Comparable<T>>(root: T ) {

    // equivalent to:
    // private final MutableList<Node> _leaves = mutableListOf(new Node(root))
    private val _leaves = mutableListOf(Node(root))

    private val queue: PriorityQueue<Node> = HeapArrayQueue(4)
    init {
        queue.insert(Node(root))
    }


    // read only view of _leaves (does not copy)
    val leaves: List<Node> get() = _leaves

    abstract fun childGen(pred: T) : List<T>

    fun peekBest(): Node? = queue.get()

    open fun best(): Node? = queue.pop()

    open inner class Node(val data: T, private val _children: MutableList<Node> = mutableListOf(), val depth: Int = 0) : Comparable<Node>{
        override fun compareTo(other: Node): Int {
            return if (depth > other.depth) 1 else this.data.compareTo(other.data)
        }

        var solved: Boolean = false

        // read-only view of _children (does not copy)
        val children: List<Node> get() = _children

        fun expand(){
            if (_children.isEmpty() && !solved) {
                childGen(data).forEach {
                    val x = Node(it,depth = depth + 1)
                    _children.add(x)
                    _leaves.add(x)
                    queue.insert(x)
                }
                if(_children.isEmpty()){

                }else{
                    _leaves.remove(this)
                }
            }else{
                throw Exception("Non-empty children")
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            // don't remove, not redundant
            other as AndTree<*>.Node

            if (data != other.data) return false
            if (_children != other._children) return false
            if (depth != other.depth) return false

            return true
        }

        override fun hashCode(): Int {
            var result = data.hashCode() ?: 0
            result = 31 * result + _children.hashCode()
            result = 31 * result + depth
            return result
        }

    }

}