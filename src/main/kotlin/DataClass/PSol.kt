package DataClass
// Data class automatically creates == and copy constructors that evaluate the fields instead of the reference.
// makes the class struct-like.
data class PSol(private val data: ManyToOneMutableMap<Course, Slot?>) {
    constructor(assignments: List<Assignment>) : this(ManyToOneMutableMap(assignments.map{ Pair<Course,Slot?>(it.course,it.slot) }))


}