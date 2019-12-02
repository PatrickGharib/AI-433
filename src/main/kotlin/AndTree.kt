import DataClass.HeapArrayQueue

import java.lang.Exception

import java.util.PriorityQueue


abstract class AndTree<T: Comparable<T>>(root: T ) {

    // equivalent to:
    // private final MutableList<Node> _leaves = mutableListOf(new Node(root))
    private val _leaves = mutableListOf(Node(root))

    var depthmode = true

    private val depthFirst = PriorityQueue<Node>{ a, b ->
        when{
            a.depth < b.depth -> 1
            a.depth > b.depth -> -1
            else -> a.data.compareTo(b.data)
        }
    }

    private val queue: PriorityQueue<Node> = PriorityQueue<Node>(4)
    init {
        val x = Node(root)
        queue.add(x)
        depthFirst.add(x)
    }


    // read only view of _leaves (does not copy)
    val leaves: List<Node> get() = _leaves

    abstract fun childGen(pred: T) : List<T>

    fun peekBest(): Node? = queue.peek()

    open fun best(): Node? = queue.poll()

    fun peekDeepest(): Node? = depthFirst.peek()

    open fun deepest(): Node? {
        val x = depthFirst.poll()
        queue.remove(x)
        return x
    }

    open inner class Node(val data: T, private val _children: MutableList<Node> = mutableListOf(), val depth: Int = 0) : Comparable<Node>{

        override fun compareTo(other: Node): Int {
            val x = this.data.compareTo(other.data)
            if (x != 0) return x
            return when {
                depth < other.depth -> 1
                depth > other.depth -> -1
                else -> 0
            }
        }

        var solved: Boolean = false

        // read-only view of _children (does not copy)
        val children: List<Node> get() = _children

        fun expand(){
            if (_children.isEmpty()) {
                childGen(data).forEach {
                    val x = Node(it,depth = depth + 1)
                    _children.add(x)
                    _leaves.add(x)
                    queue.add(x)
                    if (depthmode) depthFirst.add(x)
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