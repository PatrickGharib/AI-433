package DataClass
// Data class automatically creates == and copy constructors that evaluate the fields instead of the reference.
// makes the class struct-like.
data class PSol(val assignments: List<Assignment>) {

    // creates a copy of the pSol that has one item reassigned.
    fun copyEdit(a: Assignment) : PSol {

        val x = assignments.toMutableList()
        x.replaceAll{
            if (a.course == it.course){
                a;
            }else{
                it;
            }
        }
        return PSol(x);
    }
}