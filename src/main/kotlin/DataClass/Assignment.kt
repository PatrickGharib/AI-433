package DataClass

data class Assignment(val course: Course, val courseSlot: CourseSlot?) {
    fun toPair() : Pair<Course, CourseSlot?> {
        return Pair(course,courseSlot)
    }
}
