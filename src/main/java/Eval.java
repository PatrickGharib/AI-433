import DataClass.Assignment;
import DataClass.PSol;

/* Assignment attributes:
preference_value of each assignment in assign.
*/


/* Class attributes:

For each course below coursemin we will get pen_coursemin and for each lab pen_labsmin added to the Eval-value of an assignment.
For each assignment in assign, add up the preference-values for a course/lab that refer to a different slot as the penalty that is added to the Eval-value of assign
For every pair(a,b) statement, for which assign(a) is not equal to assign(b), you have to add pen_notpaired to the Eval-value of assign.
For each pair of sections that is scheduled into the same slot, we add a penalty pen_section to the Eval-value of an assignment assign.
*/

/*
pen_coursemin, pen_labsmin per course/lab
preference-value. function: (courses + Labs) x Slots -> Int

*/

public class Eval {

    //Attributes
    int pen_coursemin;              //for each course below coursemin
    int pen_labsmin;                //for each lab below labmin
    int pen_notpaired;              //for each pair(a,b) for which assign(a) != assign(b)
    int pen_selection;              //for each pair of sections that is schedule into the same slot

    //Constructors
    public Eval(){
        pen_coursemin = 0;
        pen_labsmin = 0;
        pen_notpaired = 0;
        pen_selection = 0;
    }

    public Eval(int pen_coursemin, int pen_labsmin, int pen_notpaired, int pen_selection){
        this.pen_coursemin = pen_coursemin;
        this.pen_labsmin = pen_labsmin;
        this.pen_notpaired = pen_notpaired;
        this.pen_selection = pen_selection;
    }

    //Functions
    public int eval(PSol pr){

        return 0;
    }

}
