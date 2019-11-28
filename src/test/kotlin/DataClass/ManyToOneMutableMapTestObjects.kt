package DataClass

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class ManyToOneMutableMapTestObjects {

    var x = ManyToOneMutableMap<String,String?>()
    @BeforeEach
    fun Setup(){
        x = ManyToOneMutableMap<String,String?>()
        x.set("A","1")
        x.set("B","1")
        //x.set("B","2")
        x.set("C","2")
        x.set("D","2")
    }


    @Test
    fun get() {
        assertEquals(x.get("A"), "1")
        assertEquals(x.get("B"), "1")
        assertNotEquals(x.get("B"),"2")
        assertEquals(x.get("C"),"2")
        assertEquals(x.get("D"),"2")
    }

    @Test
    fun set() {
        x.set("F","1")
        assertEquals(x.get("F"),"1")
        x.set("G",null)
        assertEquals(x.get("G"),null)
    }

    @Test
    fun getKeys() {
        assertEquals(x.getKeys(null), setOf<String>())
        x.set("G",null)
        assertEquals(x.get("G"),null)


        assertEquals(x.getKeys(null), setOf<String>("G"))
        x.set("G","1")
        assertEquals(x.getKeys(null), setOf<String>())
    }

    @Test
    fun getKeySet() {
    }

    @Test
    fun getValSet() {
    }

    @Test
    fun deepCopy(){

        x.set("G",null)

        val y = x.deepCopy()
        y.set("G","15")
        assertNotEquals(x.get("G"),y.get("G"))
    }
}