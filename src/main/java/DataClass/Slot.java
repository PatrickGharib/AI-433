package DataClass;

import java.util.Objects;

public class Slot {
    private String day;
    private String startTime;
    private int courseMax;
    private int courseMin;

    public Slot(String day, String startTime, int courseMax, int courseMin)
    {
        this.day = day;
        this.startTime = startTime;
        this.courseMax = courseMax;
        this.courseMin = courseMin;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
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
