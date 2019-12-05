package DataClass

import java.util.Comparator

@Deprecated("Use java queue instead")
class HeapArrayQueue<T>(size: Int, val comparator: Comparator<T>? = null) : PriorityQueue<T>() {


    var size: Int = 0
        private set

    private var data: Array<T?> = Array<Comparable<T>?>(size, {null}) as Array<T?>

    override fun insert(item: T) {
        if (size +1 == data.size){
            resize()
        }

        data[++size] = item
        lift(size)
    }

    private fun resize() {
        val old = data
        data = Array<Comparable<T>?>(size * 2, {null}) as Array<T?>
        System.arraycopy(old, 0, data, 0 , size+1)
    }

    private fun lift(size: Int) {
        var n = size
        while (n > 1 && greater(data, n/2, n, comparator)){
            data.swap(n, n/2)
            n /= 2
        }
    }

    override fun pop(): T? {
        if (size == 0) return null
        var res = get()
        data.swap(1, size--)

        drop(1)
        data[size+1] = null
        if ((size>0) && (size == (data.size-1)/4)){
            resize()
        }
        return res
    }

    private fun greater(arr: Array<T?>, i: Int, j :Int, comparator: Comparator<T>? = null): Boolean{
        if (comparator != null){
            return comparator.compare(arr[i], arr[j]) > 0
        }else{
            val left = arr[i]!! as Comparable<T>
            return left > arr[j]!!
        }
    }

    private fun drop(i: Int) {
        var k = i
        while (2*k <= size){
            var j = 2*k
            if (j<size && greater(data, j,  j+1, comparator)) j++
            if (!greater(data, k,j,comparator)) break
            data.swap(k, j)
            k = j
        }
    }

    override fun get(): T? {
        return data[1]
    }
    private fun <T> Array<T>.swap(i: Int, size: Int) {
        val x = this[i]
        this[i] = this[size]
        this[size] = x
    }
}


