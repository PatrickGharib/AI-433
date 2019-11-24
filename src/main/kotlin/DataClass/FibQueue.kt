package DataClass

@Deprecated("Unfinished")
abstract class FibQueue<T> : PriorityQueue<T>() {

    override fun get(): T? {
        return best?.data
    }

    override fun pop(): T?{
        val x = best
        if(x != null) {
            forest.remove(x)
            forest.addAll(x.children)


        }
        return x?.data
    }

    override fun insert(item: T){
        val x = Node(item)
        forest.add(x)
        if (best != null){
            if (value(item) < value(best!!.data)) {
                best = x
            }
        }else{
            best = x
        }
    }

    abstract fun value(item: T): Int

    private var best: Node<T>? = null


    // Priority queue according to https://en.wikipedia.org/wiki/Fibonacci_heap

    // O(log n) pops, O(1) insertions.

    private val forest: MutableList<Node<T>> = mutableListOf()

    private data class Node<T>(val data: T,val children: MutableList<Node<T>> = mutableListOf()){

    }

}