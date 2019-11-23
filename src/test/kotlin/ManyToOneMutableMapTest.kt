import DataClass.ManyToOneMutableMap
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ManyToOneMutableMapTest {

    private var m  = ManyToOneMutableMap<String, Int>()
    @BeforeEach
    fun setUp() {
        m  = ManyToOneMutableMap<String, Int>()
        m.set("A",1)
        m.set("B",1)
        m.set("C",2)
        m.set("D",2)
        m.set("E",2)
    }

    @Test
    fun get() {
        assertEquals(m.get("A"),1)
        assertEquals(m.get("B"),1)
        assertEquals(m.get("C"),2)
        assertEquals(m.get("D"),2)
        assertEquals(m.get("E"),2)
    }

    @Test
    fun set() {
        m.set("F",15)
        assertEquals(m.get("F"),15)
    }

    @Test
    fun getKeys() {
        assertEquals(m.getKeys(1), setOf("A","B"))
        assertEquals(m.getKeys(2), setOf("C","D","E"))
        assertNotEquals(m.getKeys(3), setOf("A","B","C"))
        assertEquals(m.getKeys(3), setOf<String>())
    }
}