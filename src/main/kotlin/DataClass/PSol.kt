package DataClass

import IO.ParsedData
import Tree.Eval

// Data class automatically creates == and copy constructors that evaluate the fields instead of the reference.
// makes the class struct-like.
data class PSol(private val data: ManyToOneMutableMap<Course, Slot?>) : Comparable<PSol> {

    val loadFactor = (data.valSet.filterNotNull().map { data.getKeys(it).size/it.max }.sum())/(ParsedData.LAB_SLOT.size+ParsedData.COURSE_SLOTS.size)

    override fun compareTo(other: PSol): Int {
        return if (this.loadFactor < other.loadFactor) 1 else (if (other.loadFactor > this.loadFactor) -1 else (if (this.value > other.value) 1 else (if (this.value< other.value) -1 else 0)))
    }

    constructor(assignments: List<Assignment>) : this(ManyToOneMutableMap(assignments.map{ Pair<Course, Slot?>(it.course,it.courseSlot) }))

    val complete: Boolean = slotLookup(null).isEmpty()
    val value: Int = Eval.getInstance(ParsedData.PAIR, ParsedData.PREFERENCES).eval(this)

    // returns true if no keys are mapped to null.

    // makes a new copy of psol with the provided assignment applied.
    fun assign(course: Course, slot: Slot) : PSol{
        val x = data.deepCopy()
        if (x!=data) throw Exception("WTF")
        if (data.get(course) == null){
            x.set(course,slot)
            if (x.get(course) == null) throw Exception("???????????")
            return PSol(x)
        }else{
            throw IllegalStateException("Course already assigned")
        }

    }

    fun courseSet() = data.keySet

    fun slotSet() = data.valSet

    fun courseLookup(c: Course): Slot?{
        return data.get(c)
    }

    fun slotLookup(s: Slot?): Set<Course>{
        return data.getKeys(s)
    }

}