package DataClass

abstract class PriorityQueue<T> {


    private var root: Node? = null
    private inner class Node(val value: T,val priority: Int, var left: Node? = null,var right: Node? = null){

    }
}