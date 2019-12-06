import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

import java.util.concurrent.PriorityBlockingQueue


abstract class AndTree<T: Comparable<T>>(root: T ) {

    // equivalent to:
    // private final MutableList<Node> _leaves = mutableListOf(new Node(root))

    var depthmode = true

    val queue: AbstractQueue<Node> = PriorityBlockingQueue<Node>(4)
    init {
        val x = Node(root)
        queue.add(x)
    }

    abstract fun childGen(pred: T) : List<T>

    fun peekBest(): Node? = queue.peek()

    open fun best(): Node? = queue.poll()


    open inner class Node(val data: T, private val _children: MutableList<Node> = mutableListOf(), val depth: Int = 0) : Comparable<Node>{


        override fun compareTo(other: Node): Int {
            return depthFirstCompare(other)
        }
        private fun depthFirstCompare(other:Node) : Int{
            return when {
                depth < other.depth -> 1
                depth > other.depth -> -1
                else -> when{

                    else -> this.data.compareTo(other.data)
                }
            }
        }
        private fun bestFirstCompare(other: Node) : Int{
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

        fun expand(distQueue: ConcurrentLinkedQueue<Node>) {
                childGen(data).forEach {
                    val x = Node(it,depth = depth + 1)
                    _children.add(x)
                    //_leaves.add(x)
                    distQueue.add(x)
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