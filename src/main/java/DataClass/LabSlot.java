package DataClass;
import java.util.Arrays;
public class LabSlot extends Slot {

    public LabSlot(Day day, float startTime, int labMax, int labMin) {
        super(day, startTime, labMax, labMin, day.equals(Day.valueOf("FR")) ? Arrays.asList(8f,9f,10f,11f,12f,13f,14f,15f,16f) : Arrays.asList(8f, 10f, 12f, 14f, 16f, 18f));
        endCalc(getStartTime());
    }

    public LabSlot(String day, float startTime, int labMax, int labMin) {
        this(Day.valueOf(day), startTime, labMax, labMin);
    }

    private void endCalc(float startTime)
    {
        if(day.equals(Day.valueOf("FR")))
            endTime = startTime + 2;
        else
            endTime = startTime + 1;
    }
}

