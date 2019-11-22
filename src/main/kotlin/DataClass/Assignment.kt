package DataClass


data class Assignment(val course: Course, val slot: Slot?){
    fun toPair() : Pair<Course, Slot?> {
        return Pair(course,slot)
    }
}
