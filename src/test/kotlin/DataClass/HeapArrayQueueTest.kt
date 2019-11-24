package DataClass

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HeapArrayQueueTest {

    var x = HeapArrayQueue<Int>(4)
    @BeforeEach
    fun setUp() {
        x =  HeapArrayQueue(4)
    }

    @Test
    fun getSize() {
        assertEquals(0,x.size )
        x.insert(1)
        x.insert(2)
        x.insert(3)
        assertEquals(3,x.size)
        x.insert(0)
        assertEquals(4,x.size)
    }

    @Test
    fun insert() {
        x.insert(1)
        x.insert(2)
        x.insert(3)

    }

    @Test
    fun pop() {
        x.insert(1)
        x.insert(2)
        x.insert(3)
        assertEquals(1,x.pop())
        assertEquals(2,x.pop())
        x.insert(0)
        assertEquals(0,x.pop())
    }

    @Test
    fun get() {
        x.insert(1)
        x.insert(2)
        x.insert(3)
        assertEquals(1,x.get())
        assertEquals(1,x.get())
        x.pop()
        assertEquals(2,x.get())
        x.insert(0)
        assertEquals(0,x.get())
    }
}