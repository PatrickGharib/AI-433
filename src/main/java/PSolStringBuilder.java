import DataClass.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class PSolStringBuilder {
    private PSol sol;

    public PSolStringBuilder(PSol sol) {
        this.sol = sol;
    }

    public PSol getSol() {
        return sol;
    }


    private StringBuilder evalBuilder(int evalValue){
        StringBuilder sb = new StringBuilder();
        sb.append("eval-value = " );
        sb.append(evalValue);
        sb.append("\n");
        sb.append("courses");
        return sb;
    }

    private StringBuilder courseSlotBuilder(Course course){
        StringBuilder sb = new StringBuilder();
        Slot slot = this.sol.courseLookup(course);          //now we have the course and it's slot

    }

    //if instance of lab add the other lab stuffs
    private StringBuilder courseToString(Course course){
        StringBuilder sb = new StringBuilder();

    }

    private StringBuilder slotToString(Slot slot){
        StringBuilder sb = new StringBuilder();
        sb.append(slot.getDay().name());
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

    private void printer(ArrayList<String> lines){
        System.out.println("Finished Calculating: Printing...");
        System.out.println(evalBuilder(10));
        for(String each:lines){
            System.out.println(each);
        }

    }
}
