package DataClass;

import java.util.Objects;

public class Slot extends SlotAbs {
    private int courseMax;
    private int courseMin;

    public Slot(String day, String startTime, int courseMax, int courseMin) {
        super(day, startTime);
        this.courseMax = courseMax;
        this.courseMin = courseMin;
    }


    public int getCourseMax() {
        return courseMax;
    }

    public int getCourseMin() {
        return courseMin;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Slot)
        {
            Slot them = (Slot) o;
            return (getDay()+getStartTime()).equals(them.getDay() + them.getStartTime());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, courseMax, courseMin);
    }
}
