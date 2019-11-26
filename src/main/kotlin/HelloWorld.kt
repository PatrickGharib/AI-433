import DataClass.Assignment
import DataClass.CourseSlot
import DataClass.PSol
import IO.ParsedData
import IO.Parser
import java.nio.file.Paths

fun main() {
    Parser.inputReader("deptinst1.txt")
    print(Paths.get("").toAbsolutePath())
    CourseSchedulerProcess(constructPSol()).execute()
}

fun constructPSol() : PSol{
    val x: MutableList<Assignment> = mutableListOf()
    ParsedData.PARTIAL_ASSIGNMENTS.forEach {
        x.add(Assignment(it.course, it.slot as CourseSlot?))
    }
    val exclude = ParsedData.PARTIAL_ASSIGNMENTS.map { it.course }
    ParsedData.COURSES.forEach {
        if (it in exclude){
            return@forEach
        }else {
            x.add(Assignment(it, null))
        }
    }
    return PSol(x)
}
