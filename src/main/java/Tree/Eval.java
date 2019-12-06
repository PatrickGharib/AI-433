package Tree;

import DataClass.*;

import java.lang.reflect.Array;
import java.util.*;
import DataClass.PreferredCoursePair;
import IO.ParsedData;

/*
@author Zahra Ghavasieh

Singleton class: Eval

Soft Constraints:
For each course below coursemin we will get pen_coursemin and for each lab pen_labsmin added to the Tree.Eval-value of an assignment.
For each assignment in assign, add up the preference-values for a course/lab that refer to a different slot as the penalty that is added to the Tree.Eval-value of assign.
For every pair(a,b) statement, for which assign(a) is not equal to assign(b), you have to add pen_notpaired to the Tree.Eval-value of assign.
For each pair of sections that is scheduled into the same slot, we add a penalty pen_section to the Tree.Eval-value of an assignment assign.

Design:
Eval is designed using four subfunctions
 Evalminfilled, Evalpref, Evalpair and Evalsecdiff that are producing a value for each of the 4 types of soft constraints.
 Then we have
    Eval(assign) = Evalminfilled(assign) * wminfilled + Evalpref(assign) * wpref + Evalpair * wpair + Evalsecdiff(assign) * wsecdiff
*/

public class Eval {

    //Singleton Attribute
    private static Eval eval_instance = null;

    //Penalties
    private static int pen_coursemin = 1;                          //for each course below coursemin
    private static int pen_labsmin = 1;                            //for each lab below labmin
    private static int pen_section = 1;                            //for each pair of sections that is schedule into the same slot
    private static int pen_notpaired = 1;                          //for each pair(a,b) for which assign(a) != assign(b)

    //Weights
    private static int w_minfilled = 1;                            //CourseMin & LabsMin
    private static int w_pref = 1;                                 //Prederred assignments
    private static int w_pair = 1;                                 //Preferred pairs
    private static int w_secdiff = 1;                              //Sections in different slots

    //Pairs
    private static Set<PreferredCoursePair> coursePairs;                   //Preferred course pairs with same assign value
    private static HashMap<Course, HashMap<Slot, Integer>> prefAssigns;    //Preferred course assignments
    private static HashMap<Course, Integer> pen_prefAssign;                //for each course/lab not assigned to its preferred slot


    //Private Constructor
    private Eval(int pen_coursemin, int pen_labsmin, int pen_section, int pen_notpaired,
                int wminfilled, int wpref, int wpair, int wsecdiff,
                 Set<PreferredCoursePair> pairs, Set<PreferredCourseTime> prefAssigns){

        //Update eval attributes
        setWeights(wminfilled, wpref, wpair, wsecdiff);
        setPenalties(pen_coursemin, pen_labsmin, pen_section, pen_notpaired);
        Eval.coursePairs = pairs;

        //Build hashmap for preferred assignments
        Course course;
        Integer oldVal;
        HashMap<Slot, Integer> prefs = new HashMap<>();
        Eval.prefAssigns = new HashMap<>();
        Eval.pen_prefAssign = new HashMap<>();

        for (PreferredCourseTime pref : prefAssigns){

            course = pref.getCourse();

            //Update pref-value hashmap
            prefs.put(pref.getSlot(), pref.getPreferenceVal());
            Eval.prefAssigns.put(course, prefs);


            //Update penalty
            oldVal = pen_prefAssign.get(course);
            if (oldVal == null)
                pen_prefAssign.put(course,pref.getPreferenceVal());
            else
                pen_prefAssign.put(course, oldVal+pref.getPreferenceVal());
        }
    }

    //Instantiation with default penalties
    public static Eval getInstance(Set<PreferredCoursePair> pairs, Set<PreferredCourseTime> prefAssigns){
        if (eval_instance == null)
            eval_instance = new Eval(1,1,1,1,1, 1, 1, 1, pairs, prefAssigns);
        return eval_instance;
    }

    //Instantiation with custom weights
    public static Eval getInstance(int wminfilled, int wpref, int wpair, int wsecdiff,
                                   Set<PreferredCoursePair> pairs, Set<PreferredCourseTime> prefAssigns){
        if (eval_instance == null) {
            eval_instance = new Eval(1,1,1,1,wminfilled, wpref, wpair, wsecdiff, pairs, prefAssigns);
        }
        return eval_instance;
    }

    //Instantiation with custom weights and penalties
    public static Eval getInstance(int pen_coursemin, int pen_labsmin, int pen_section, int pen_notpaired,
                                   int wminfilled, int wpref, int wpair, int wsecdiff,
                                   Set<PreferredCoursePair> pairs, Set<PreferredCourseTime> prefAssigns){
        if (eval_instance == null) {
            eval_instance = new Eval( pen_coursemin, pen_labsmin, pen_section, pen_notpaired,
                    wminfilled, wpref, wpair, wsecdiff, pairs, prefAssigns);
        }
        return eval_instance;
    }

    //Setter for penalties
    public void setPenalties(int pen_coursemin, int pen_labsmin, int pen_section, int pen_notpaired){
        Eval.pen_coursemin = pen_coursemin;
        Eval.pen_labsmin = pen_labsmin;
        Eval.pen_section = pen_section;
        Eval.pen_notpaired = pen_notpaired;
    }

    //Setter for Weights (use setter for instance)
    public void setWeights(int wminfilled, int wpref, int wpair, int wsecdiff){
        Eval.w_minfilled = wminfilled;
        Eval.w_pref = wpref;
        Eval.w_pair = wpair;
        Eval.w_secdiff = wsecdiff;
    }


    //Functions
    public int eval(PSol sol){

        int evaluation = 0;
        Set<Slot> slots = sol.slotSet();
        for (Slot slot : slots) {

            if (slot == null) {
                continue;
            }

            int coursenum = 0, labnum = 0;                      //Count courses and labs assigned to each slot
            List<String> sections = new ArrayList<>();          //Keep track of (course) sections assigned to each slot

            for (Course course : sol.slotLookup(slot)){
                //Count how many courses and labs are assigned
                if (course instanceof Lab){
                    labnum++;
                } else{
                    coursenum++;
                    //Check if same section appears in slot
                    if (w_secdiff != 0)
                        evaluation += w_secdiff * eval_secdiff(course, sections);
                }
                //Add up preference values
                if (w_pref != 0) {
                    //evaluation += w_pref * eval_pref(course, slot);
                }
            } //For each course in slot

            //Check CourseMin and LabsMin
            if (w_minfilled != 0 && sol.getComplete())
                evaluation += w_minfilled * eval_minFilled(sol, slot, coursenum, labnum);
        } //For each slot in sol

        //Check if a pair has the same assignment
        if (w_pair != 0)
            evaluation += w_pair * eval_pair(sol);

        // temporary naive preferred time eval.
        int aEval = naivePrefEval(sol);
        evaluation += w_pref * aEval;

        return evaluation;
    }
    

    private int naivePrefEval(PSol sol) {
        int aEval = 0;
        for (PreferredCourseTime p : ParsedData.PREFERENCES){
            Slot s = sol.courseLookup(p.getCourse());

            // if its unassigned ignore it (don't increment)
            if (s == null){
                continue;
            }
            // if it IS assigned, but doesn't match the current pref course time, increment the penalty.
            if (s != p.getSlot()){
                aEval+= p.getPreferenceVal();
            }
        }
        return aEval;
    }


    //For each course below coursemin we will get pen_coursemin and for each lab pen_labsmin added to the Eval-value
    private int eval_minFilled(PSol sol, Slot slot, int coursenum, int labnum){

        int evaluation = 0;

        if (slot instanceof CourseSlot) {
            if (coursenum < slot.getMin()) {
                evaluation += pen_coursemin;
            }
        } else {
            if (labnum < slot.getMin()) {
                evaluation += pen_labsmin;
            }
        }

        return evaluation;
    }

    //For each assignment, add up the preference-values for a course/lab that refers to a different slot as the penalty that is added to the Eval-value of assign.
    private int eval_pref(Course course, Slot slot){

        int evaluation = 0;
        Integer base = pen_prefAssign.get(course);

        if (base != null){
            evaluation += base;
            Integer prefVal = prefAssigns.get(course).get(slot);
            if (prefVal != null)
                evaluation -= prefVal;
        }

        return evaluation;
    }

    //For every pair(a,b) statement, for which assign(a) != assign(b), add pen_notpaired to the Eval-value
    private int eval_pair(PSol sol){

        int evaluation = 0;

        for (PreferredCoursePair pair : coursePairs){
            Slot s1 = sol.courseLookup(pair.getCourse1());
            Slot s2 = sol.courseLookup(pair.getCourse2());
            if ( (s1 != null) && (s2 != null) && (!s1.equals(s2))) {
                evaluation += pen_notpaired;
            }
        }

        return evaluation;
    }

    private int eval_secdiff(Course course, List<String> sections){

        int evaluation = 0;
        String section = ((Section)course).getSection();

        if (sections.contains(section)){
            evaluation += pen_section;
        } else{
            sections.add(section);              //Original list gets changed
        }

        return evaluation;
    }
}
