package DataClass

data class Assignment(val course: Course, val courseSlot: Slot?) {
    fun toPair() : Pair<Course, Slot?> {
        return Pair(course,courseSlot)
    }
}
