package DataClass;
import java.util.Arrays;
import java.util.Collections;

public class LabSlot extends Slot {

    public LabSlot(Day day, float startTime, int labMax, int labMin) {
        super(day, startTime, labMax, labMin,
                day.equals(Day.MO) || day.equals(Day.TU) ? Arrays.asList(8f,9f,10f,11f,12f,13f,14f,15f,16f,17f,18f,19f,20f) :
                day.equals(Day.FR) ? Arrays.asList(8f,10f,12f,14f,16f,18f) :
                        Collections.emptyList());
        endCalc();
    }

    public LabSlot(String day, float startTime, int labMax, int labMin) {
        this(Day.valueOf(day), startTime, labMax, labMin);
    }

    private void endCalc()
    {
        endTime = day.equals(Day.FR) ? startTime + 2f : startTime + 1f;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof LabSlot;
    }

}

