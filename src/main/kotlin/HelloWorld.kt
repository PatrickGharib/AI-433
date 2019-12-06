import DataClass.*
import IO.ParsedData
import IO.Parser
import Tree.Constr
import Tree.Eval
import java.nio.file.Paths

fun main(args: Array<String>) {
    //println(args.size)
    val file = if (args.isNotEmpty()){
        args[0]
    } else{
        "minnumber.txt"
    }
    Parser.inputReader(file)
    //Parser.inputReader("testsmall.txt")

    //println(ParsedData.COURSE_SLOTS)

    //println(Paths.get("").toAbsolutePath())



    if (args.size == 5){
        Eval.getInstance(args[1].toInt(),args[2].toInt(),args[3].toInt(),args[4].toInt(),ParsedData.PAIR,ParsedData.PREFERENCES)
    }
    else if (args.size == 9){
        Eval.getInstance(args[1].toInt(),args[2].toInt(),args[3].toInt(),args[4].toInt(),args[5].toInt(),args[6].toInt(),args[7].toInt(),args[8].toInt(),ParsedData.PAIR,ParsedData.PREFERENCES)
    }

    val time = if (args.size >= 10){
        args[9].toLong()
    }else{
        15
    }

    
    val threads = if (args.size == 11){
        args[10].toInt()
    }else{
        4
    }

    val y = constructPSol();
    //println("Valid: ${Constr.getInstance(ParsedData.NOT_COMPATIBLE, ParsedData.UNWANTED).constrPartial(y)}")
    //println(PSolStringBuilder(y).ToString(y.value))

    val x = CourseSchedulerProcess(y, time, threads).execute()

    if( x == null){
        println(" No soloution")
    }else {
        println(x?.value)
        Eval.getInstance(ParsedData.PAIR, ParsedData.PREFERENCES).eval(x)
        println("done")
        x?.value?.let { PSolStringBuilder(x).ToString(it) }
    }



}

fun constructPSol() : PSol {
    val x: MutableList<Assignment> = mutableListOf()
    ParsedData.PARTIAL_ASSIGNMENTS.forEach {
        x.add(Assignment(it.course, it.slot))
    }

    val sorted_courses = (ParsedData.COURSES + ParsedData.LABS).map{ it2 ->
        var complexity = 0
        ParsedData.UNWANTED.forEach {
            if (it.course == it2) complexity++
        }

        ParsedData.NOT_COMPATIBLE.forEach {
            if (it.course1 == it2 || it.course2 == it2) complexity++

        }

        ParsedData.PREFERENCES.forEach {
            if (it.course == it2) complexity++
        }

        ParsedData.PAIR.forEach {
            if (it.course1 == it2 || it.course2 == it2) complexity++
        }

        if (it2 is Section && it2.lecNum >= 9) complexity +=100

        if(it2.courseNumber>=500) complexity+=100

        if (it2.courseNumber >= 800) complexity +=1000

        //println("Complexity: $complexity Name: ${it2.courseName} ${it2.courseNumber}")
        Pair(complexity,it2)
    }.sortedByDescending { it.first }.map{ it.second }

    //println(sorted_courses.map { course -> course.courseName + course.courseNumber.toString() })
    val exclude = ParsedData.PARTIAL_ASSIGNMENTS.filter { it.course != null }.map { it.course }
    /*
    ParsedData.COURSES.forEach {
        if (it in exclude) {
            return@forEach
        } else {
            x.add(Assignment(it, null))
        }
    }

    ParsedData.LABS.forEach {
        if (it in exclude) {
            return@forEach
        } else {
            x.add(Assignment(it, null))
        }
    }
    */
    x.addAll(sorted_courses.filter { it !in exclude }.map { Assignment(it,null) })

    return PSol(x)
}
