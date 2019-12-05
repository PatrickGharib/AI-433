import DataClass.Assignment
import DataClass.CourseSlot
import DataClass.PSol
import IO.ParsedData
import IO.Parser
import Tree.Eval
import java.nio.file.Paths

fun main(args: Array<String>) {
    //println(args.size)
    val file = if (args.isNotEmpty()){
        args[0]
    } else{
        "testsmall.txt"
    }
    Parser.inputReader(file)
    //Parser.inputReader("testsmall.txt")


    println(Paths.get("").toAbsolutePath())
    val y = constructPSol();


    if (args.size == 4){
        Eval.getInstance(args[1].toInt(),args[2].toInt(),args[3].toInt(),args[4].toInt(),ParsedData.PAIR,ParsedData.PREFERENCES)
    }
    //println(PSolStringBuilder(y).ToString(y.value))
    val x = CourseSchedulerProcess(y).execute()
    println(x?.value)
    Eval.getInstance(ParsedData.PAIR,ParsedData.PREFERENCES).eval(x)
    println("done")
    x?.value?.let { PSolStringBuilder(x).ToString(it) }

}

fun constructPSol() : PSol{
    val x: MutableList<Assignment> = mutableListOf()
    ParsedData.PARTIAL_ASSIGNMENTS.forEach {
        if (it.course !=null) {
            x.add(Assignment(it.course, it.slot))
        }
    }

    val exclude = ParsedData.PARTIAL_ASSIGNMENTS.filter { it.course!=null }.map { it.course }
    ParsedData.COURSES.forEach {
        if (it in exclude){
            return@forEach
        }else {
            x.add(Assignment(it, null))
        }
    }

    ParsedData.LABS.forEach {
        if (it in exclude){
            return@forEach
        }else{
            x.add(Assignment(it, null))
        }
    }
    return PSol(x)
}
