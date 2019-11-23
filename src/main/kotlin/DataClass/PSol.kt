package DataClass
// Data class automatically creates == and copy constructors that evaluate the fields instead of the reference.
// makes the class struct-like.
data class PSol(private val data: ManyToOneMutableMap<Course, SlotAbs?>) {
    constructor(assignments: List<Assignment>) : this(ManyToOneMutableMap(assignments.map{ Pair<Course,SlotAbs?>(it.course,it.slot) }))

    val value: Int = 0 // eval value of solution



    // returns true if no keys are mapped to null.
    val complete: Boolean = data.getKeys(null).isEmpty()

    // makes a new copy of psol with the provided assignment applied.
    fun assign(course: Course, slot: SlotAbs) : PSol{
        val x = data.copy()
        if (data[course] == null){
            x.set(course,slot)
            return PSol(x)
        }else{
            throw IllegalStateException("Course already assigned")
        }

    }

    fun courseSet() = data.keySet

    fun slotSet() = data.valSet

    fun courseLookup(c: Course): SlotAbs?{
        return data[c]
    }
    fun slotLookup(s: SlotAbs?): Set<Course>{
        return data.getKeys(s)
    }

}