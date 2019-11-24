package DataClass

abstract class PriorityQueue<T> {

    abstract fun get(): T?
    abstract fun insert(item: T)
    abstract fun pop(): T?
}