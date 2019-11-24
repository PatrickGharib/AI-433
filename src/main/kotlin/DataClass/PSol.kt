package DataClass
// Data class automatically creates == and copy constructors that evaluate the fields instead of the reference.
// makes the class struct-like.
data class PSol(private val data: ManyToOneMutableMap<Course, Slot?>) {
    constructor(assignments: List<Assignment>) : this(ManyToOneMutableMap(assignments.map{ Pair<Course, Slot?>(it.course,it.courseSlot) }))

    val value: Int = 0 // eval value of solution



    // returns true if no keys are mapped to null.
    val complete: Boolean = data.getKeys(null).isEmpty()

    // makes a new copy of psol with the provided assignment applied.
    fun assign(course: Course, slot: Slot) : PSol{
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

    fun courseLookup(c: Course): Slot?{
        return data[c]
    }
    fun slotLookup(s: Slot?): Set<Course>{
        return data.getKeys(s)
    }

}