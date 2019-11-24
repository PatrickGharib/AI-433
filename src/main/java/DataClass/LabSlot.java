package DataClass;

public class LabSlot extends Slot {

    public LabSlot(Day day, float startTime, int labMax, int labMin) {
        super(day, startTime, labMax, labMin);
        endCalc(getStartTime());
    }

    public LabSlot(String day, float startTime, int labMax, int labMin) {
        super(Day.valueOf(day), startTime, labMax, labMin);
    }

    private void endCalc(float startTime)
    {
        if(day.equals(Day.FR))
            endTime = startTime + 2;
        else
            endTime = startTime + 1;
    }
}

