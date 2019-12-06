package Tree;

import DataClass.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/* Hard constraints:
Not more than coursemax(s) courses can be assigned to slot s.
Not more than labmax(s) labs can be assigned to slot s.
For each of not-compatible(a,b) statements, assign(a) has to be unequal to assign(b).
For each of unwanted(a,s) statements, assign(a) has to be unequal to s.
Evening classes (LEC 9+) have to be scheduled into evening slots (18:00+).
All courses (course sections) on the 500-level have to be scheduled into different time slots.
Assign(ci) needs to be unequal to assign(lik) for all k and i.
No courses can be scheduled at tuesdays 11:00-12:30.
There are two special "courses" CPSC 813 and CPSC 913 that have to be scheduled tuesdays/thursdays 18:00-19:00
 and CPSC 813 is not allowed to overlap with any labs/tutorials of CPSC 313 or with any course section of CPSC 313.
 and CPSC 913 is not allowed to overlap with any labs/tutorials of CPSC 413 or with any course section of CPSC 413.
*/


public class Constr {

    //Singleton Attribute
    private static Constr constr_instance = null;

    //Attributes
    private Set<NotCompatibleCoursePair> notCompatibles;
    private Set<UnwantedCourseTime> unwanted;

    //Constructor
    private Constr(Set<NotCompatibleCoursePair> pairs, Set<UnwantedCourseTime> unwanted){
        notCompatibles = pairs;
        this.unwanted = unwanted;
    }

    //Instantiation with no given attributes
    public static Constr getInstance(){
        if (constr_instance == null)
            constr_instance = new Constr(new LinkedHashSet<>(), new LinkedHashSet<>());
        return constr_instance;
    }

    //Instantiation with no given attributes
    public static Constr getInstance(Set<NotCompatibleCoursePair> pairs, Set<UnwantedCourseTime> unwanted){
        if (constr_instance == null)
            constr_instance = new Constr(pairs, unwanted);
        return constr_instance;
    }

    //Functions
    public boolean constr(@NotNull PSol sol){

        //Check if solution is complete
        if(! sol.getComplete())
            return false;
        else
            constrPartial(sol);

        return true;
    }

    public boolean constrPartial(@NotNull PSol sol){

        Set<Slot> slots = sol.slotSet();
        //Special Courses conditions
        Course c813 = null, c913 = null;
        ArrayList<Course> noOverlap813 = new ArrayList<>();
        ArrayList<Course> noOverlap913 = new ArrayList<>();


        for (Slot slot : slots) {

            if (slot == null) {
                continue;
            }

            //Count how many courses and labs are assigned
            int coursenum = 0, labnum = 0;
            ArrayList<Course> courses500 = new ArrayList<>();
            for (Course course : sol.slotLookup(slot)) {
                if (course instanceof Lab) {
                    labnum++;

                    //Check if lab overlaps with its lecture
                    for (Section sec : ((Lab) course).getSections()){
                        if (slot.overlaps(sol.courseLookup(sec))) {

                            //System.out.println("Fail 1");
                            /*
                            System.out.println(sec);
                            System.out.println(course);
                            System.out.println(slot);
                            System.out.println(slot.getEndTime());
                            System.out.println(sol.courseLookup(sec));
                            System.out.println(sol.courseLookup(sec).getEndTime());
                            System.out.println(slot.overlaps(sol.courseLookup(sec)));

                             */
                            return false;
                        }
                    }
                } else {
                    coursenum++;

                    //Check evening courses
                    if(((Section)course).getLecNum() >= 9) {
                        if ((int)slot.getStartTime() < 18) {
                            //System.out.println("Fail 2");
                            return false;
                        }
                    }

                    //Count 500-level courses
                    if ((course.getCourseNumber() / 100) == 5) {
                        if (courses500.size() > 0)  {
                            //System.out.println("Fail 3");
                            return false;
                        }
                        courses500.add(course);
                    }
                }

                //No courses(assuming course sections) can be scheduled at tuesdays 11:00-12:30.
                if (slot.getDay().equals(Slot.Day.TU)) {
                    if ((slot.getStartTime() == 11) || slot.getStartTime() == 12) {
                        if (coursenum > 0) {
                            //System.out.println("Fail 4");
                            return false;
                        }
                    }
                }

                //Special "course" CPSC 813 has to be scheduled tuesdays/thursdays 18:00-19:00
                if ((course.getCourseNumber() == 813) && (course.getCourseName().equals("CPSC"))){
                    if (! (slot.getDay().equals(Slot.Day.TU) && slot.getStartTime() == 18)) {
                        //System.out.println("Fail 5");
                        //System.out.println(slot);
                        return false;
                    }
                    c813 = course;
                }
                //Special "course" CPSC 913 has to be scheduled tuesdays/thursdays 18:00-19:00
                if ((course.getCourseNumber() == 913) && (course.getCourseName().equals("CPSC"))){
                    if (! (slot.getDay().equals(Slot.Day.TU) && slot.getStartTime() == 18)) {
                        //System.out.println("Fail 6");
                        return false;
                    }
                    c913 = course;
                }
                if ((course.getCourseNumber() == 313) && (course.getCourseName().equals("CPSC")))
                    noOverlap813.add(course);
                if ((course.getCourseNumber() == 413) && (course.getCourseName().equals("CPSC")))
                    noOverlap913.add(course);
            } //for each course in slot

            //Check CourseMin and LabsMin
            if (slot instanceof CourseSlot) {
                if (coursenum > slot.getMax()) {
                    //System.out.println("Fail 7");
                    return false;
                }
            } else {
                if (labnum > slot.getMax()) {
                    //System.out.println("Fail 8");
                    return false;
                }
            }
        } //for each slot in sol

        //Check if a notCompatible pair has the same assignment
        for (NotCompatibleCoursePair pair : notCompatibles) {
            Slot s1 = sol.courseLookup(pair.getCourse1());
            Slot s2 = sol.courseLookup(pair.getCourse2());
            if ((s1 != null) && (s1.equals(s2))) {
                //System.out.println("Fail 9");
                return false;
            }
        }

        //Check if an Unwanted pair exists in sol
        for (UnwantedCourseTime pair : unwanted) {
            Course c = pair.getCourse();
            Slot s = pair.getSlot();
            if (Objects.equals(sol.courseLookup(c), s)) {
                //System.out.println("Fail 10");
                return false;
            }
        }

        //Check if special courses overlap with unwanted overlaps
        //CPSC 813 is not allowed to overlap with any labs/tutorials of CPSC 313 or with any course section of CPSC 313.
        if (c813 != null){
            for (Course c : noOverlap813){
                if (Objects.requireNonNull(sol.courseLookup(c)).overlaps(sol.courseLookup(c813))) {
                    //System.out.println("Fail 11");
                    return false;
                }
            }
        }
        //CPSC 913 is not allowed to overlap with any labs/tutorials of CPSC 413 or with any course section of CPSC 413.
        if (c913 != null){
            for (Course c : noOverlap913){
                if (Objects.requireNonNull(sol.courseLookup(c)).overlaps(sol.courseLookup(c913))) {
                    //System.out.println("Fail 12");
                    return false;
                }
            }
        }

        for( Slot s : sol.slotSet()){

        }

        return true;
    } //End of constrPartial

}
