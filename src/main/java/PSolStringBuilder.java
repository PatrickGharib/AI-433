import DataClass.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class PSolStringBuilder {

    private List <Pair<Course,Slot>> convert = new ArrayList<>();

    public PSolStringBuilder(PSol sol) {
        for (Course c : sol.courseSet()){
            this.convert.add(new Pair<>(c, sol.courseLookup(c)));
        }
//        this.convert = sort(convert);

        this.convert.sort(new PSolComparator());
    }

//    //todo implement the sorting
//    private List <Pair<Course,Slot>> sort (List <Pair<Course,Slot>> unsorted){
//        return unsorted;
//    }

    //this method prints the output
    public List<String> ToString(int evalValue) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        result.add(sb.toString());
        sb.append(evalBuilder(evalValue));
        for (Pair p : this.convert){
            result.add(courseSlotBuilder(p).toString());
        }
        printer(result);
        return result;
    }

    //this method change the eval value to a string
    private StringBuilder evalBuilder(int evalValue){
        StringBuilder sb = new StringBuilder();
        sb.append("eval-value = " );
        sb.append(evalValue);
        sb.append("\n");
        sb.append("courses: \n");
        return sb;
    }

    //this method calls course and slot builder to create a single line of the output (course to slot pairing)
    private StringBuilder courseSlotBuilder(Pair<Course,Slot> pair){
        StringBuilder sb = new StringBuilder();
        sb.append(courseToString(pair.getKey()));
        sb.append("            ");
        sb.append(slotToString(pair.getValue()));
        return sb;
    }

    //if instance of lab add the other lab stuffs
    private StringBuilder courseToString(Course course){
        StringBuilder sb = new StringBuilder();
        sb.append(course.getCourseName() + " ");
        sb.append(course.getCourseNumber() + " ");
        //in the case that it is a course
        if (course instanceof Section){
            sb.append("LEC" + " ");
            sb.append(((Section)course).getLecNum());
        }
        //case that the lab is assigned a particular lecture
        else if (course instanceof Lab && ((Lab) course).getSections().size() == 1){
            sb.append("LEC ");
            Section y = (Section)(((Lab) course).getSections().toArray()[0]);
            sb.append(y.getLecNum());
            sb.append(" " + ((Lab) course).getTutLab() + " ");
            sb.append(((Lab) course).getTutLabNum());
        }
        //case that lab is open to all lecture sections
        else {
            sb.append(((Lab) course).getTutLab() + " ");
            sb.append(((Lab) course).getTutLabNum());
        }
        return sb;
    }

    //converts a time slot to a string
    private StringBuilder slotToString(Slot slot){
        StringBuilder sb = new StringBuilder();
        sb.append(slot.getDay().name());            //changes the enum back to a string
        sb.append(", ");
        sb.append((int)(slot.getStartTime()));
        sb.append(":");
        if(slot.getStartTime() - (int)slot.getStartTime() == 0){
            sb.append("00");
        }
        else{
            sb.append("30");
        }
        return sb;
    }

    //call this after changing the psol into a ist of strings using the method toString
    private void printer(List<String> lines){
        System.out.println("Finished Calculating: Printing...");
        for(String each:lines){
            System.out.println(each);
        }
    }
}
