import DataClass.Assignment;
import DataClass.PSol;

/* Hard constraints:
Not more than coursemax(s) courses can be assigned to slot s.
Not more than labmax(s) labs can be assigned to slot s.
assign(ci) has to be unequal to assign(lik) for all k and i.
The input for your system will contain a list of not-compatible(a,b) statements, with a,b in Courses + Labs. For each of those, assign(a) has to be unequal to assign(b).
The input for your system can contain a partial assignment partassign: Courses + Labs -> Slots + {$}. The assignment assign your system produces has to fulfill the condition:
assign(a) = partassign(a) for all a in Courses + Labs with partassign(a) not equal to $.
The input for your system can contain a list of unwanted(a,s) statements, with a in Courses + Labs and s in Slots. For each of those, assign(a) has to be unequal to s.
If a course (course section) is put into a slot on mondays, it has to be put into the corresponding time slots on wednesdays and fridays (in fact, this is a constraint already on the University level). So, these three time slots are treated as one abstract slot, which allows us to see our Department problem as an instantiation of the general problem!
If a course (course section) is put into a slot on tuesdays, it has to be put into the corresponding time slots on thursdays.
If a lab/tutorial is put into a slot on mondays, it has to be put into the corresponding time slots on wednesdays.
If a lab/tutorial is put into a slot on tuesdays, it has to be put into the corresponding time slots on thursdays.
All course sections with a section number starting LEC 9 are evening classes and have to be scheduled into evening slots.
All courses (course sections) on the 500-level have to be scheduled into different time slots.
No courses can be scheduled at tuesdays 11:00-12:30.
There are two special "courses" CPSC 813 and CPSC 913 that have to be scheduled tuesdays/thursdays 18:00-19:00 and CPSC 813 is not allowed to overlap with any labs/tutorials of CPSC 313 or with any course section of CPSC 313 (and transitively with any other courses that are not allowed to overlap with CPSC 313) and CPSC 913 is not allowed to overlap with any labs/tutorials of CPSC 413 or with any course section of CPSC 413 (and transitively with any other courses that are not allowed to overlap with CPSC 413). These two "courses" are my "tricky" way to deal with the fact of quizzes for CPSC 313 and CPSC 413.
*/


public class Constr {

    //Attributes

    //Constructors
    public Constr(){

    }

    //Functions
    public boolean constr(PSol pr){

        return true;
    }


}
