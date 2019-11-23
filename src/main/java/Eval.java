/*
@author Zahra Ghavasieh

Soft Constraints:
For each course below coursemin we will get pen_coursemin and for each lab pen_labsmin added to the Eval-value of an assignment.
For each assignment in assign, add up the preference-values for a course/lab that refer to a different slot as the penalty that is added to the Eval-value of assign
For every pair(a,b) statement, for which assign(a) is not equal to assign(b), you have to add pen_notpaired to the Eval-value of assign.
For each pair of sections that is scheduled into the same slot, we add a penalty pen_section to the Eval-value of an assignment assign.
*/

import DataClass.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Eval {

    //Attributes
    private int pen_coursemin;              //for each course below coursemin
    private int pen_labsmin;                //for each lab below labmin
    private int pen_section;                //for each pair of sections that is schedule into the same slot
    private int pen_notpaired;              //for each pair(a,b) for which assign(a) != assign(b)
    private PreferredCoursePair[] pairs;    //Preferred course pairs with same assign value

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
    public int eval(PSol sol, Set<PreferredCoursePair> pairs){

        int evaluation = 0;

        for (Slot slot : sol.slotSet()) {

            if (slot == null) {
                continue;
            }

            int coursenum = 0, labnum = 0;
            List<String> sections = new ArrayList<>();

            for (Course course : sol.slotLookup(slot)){
                //Count how many courses and labs are assigned
                if (course instanceof Lab){
                    labnum++;
                } else{
                    coursenum++;
                    //Check if same section appears in slot
                    String section = course.getSection();
                    if (sections.contains(section)){
                        evaluation += pen_section;
                    } else{
                        sections.add(section);
                    }
                }
            }
            //Note: you should if slot is a course or lab slot as to not assign a course to a lab slot or vice versa.
            if (coursenum < slot.getMin()){
                evaluation += pen_coursemin;
            }
            if (labnum < slot.getMin()){
                evaluation += pen_labsmin;
            }
        }

        //Check if a pair has the same assignment
        for (PreferredCoursePair pair : pairs){
            Slot s1 = sol.courseLookup(pair.getCourse1());
            Slot s2 = sol.courseLookup(pair.getCourse2());
            if ( (s1 != null) && (s2 != null) && (!s1.equals(s2))) {
                evaluation += pen_notpaired;
            }
        }

        return evaluation;
    }

}
