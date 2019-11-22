import DataClass.*;

import java.util.Arrays;

/* Assignment attributes:
preference_value of each assignment in assign. (courses + Labs) x Slots -> Int
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
    private int pen_coursemin;              //for each course below coursemin
    private int pen_labsmin;                //for each lab below labmin
    private int pen_section;                //for each pair of sections that is schedule into the same slot
    private int pen_notpaired;              //for each pair(a,b) for which assign(a) != assign(b)
    private PreferredCoursePair[] pairs;

    //Constructors
    public Eval(){
        pen_coursemin = 0;
        pen_labsmin = 0;
        pen_notpaired = 0;
        pen_section = 0;
        pairs = new PreferredCoursePair[0];
    }

    public Eval(int pen_coursemin, int pen_labsmin, int pen_notpaired, int pen_section, PreferredCoursePair[] pairs){
        this.pen_coursemin = pen_coursemin;
        this.pen_labsmin = pen_labsmin;
        this.pen_notpaired = pen_notpaired;
        this.pen_section = pen_section;
        this.pairs = pairs;
    }

    //Functions
    public int eval(PSol sol, PreferredCoursePair[] pairs){
        int evaluation = 0;
        PreferredCoursePair[] disposable_pairs = Arrays.copyOf(pairs, pairs.length);
        for (Slot slot : sol.getLinked().keySet()) {

            if (slot == null) {
                continue;
            }

            int coursenum = 0, labnum = 0;
            for (Course course : sol.getLinked().get(slot)){
                //Count how many courses and labs are assigned
                if (course instanceof Lab){
                    labnum++;
                } else{
                    coursenum++;
                }
                //Check if it's a pair
                //if (course.equals(disposable_pairs.get))

            }
            if (coursenum < slot.getCourseMin()){
                evaluation += pen_coursemin;
            }
            //if (labnum < slot.getLabMin()){
                evaluation += pen_labsmin;
            //}
        }
        return evaluation;
    }

}
