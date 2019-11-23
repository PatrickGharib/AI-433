package DataClass;

import java.util.Objects;

public class LabSlot extends SlotAbs {

    private int labMax;
    private int labMin;

    public LabSlot(String day, String startTime, int labMax, int labMin) {
        super(day, startTime);
        this.labMax = labMax;
        this.labMin = labMin;
    }

    public int getLabMax() {
        return labMax;
    }

    public int getLabMin() {
        return labMin;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof LabSlot)
        {
            LabSlot them = (LabSlot) o;
            return (getDay()+getStartTime()).equals(them.getDay() + them.getStartTime());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, labMax, labMin);
    }
}

