package DataClass;

import java.util.Objects;

public abstract class Slot {
    public enum Day
    {
        MO,
        TU,
        FR
    }

    protected Day day;
    protected float startTime;
    protected float endTime;

    public Slot(Day day, float startTime, int max, int min) {
        this.day = day;
        this.startTime = startTime;
        this.max = max;
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    protected int max;
    protected int min;

    public Day getDay() {
        return day;
    }

    public float getStartTime() {
        return startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public boolean overlaps(Slot s)
    {
        return getEndTime() >= s.getStartTime() && s.getEndTime() >= getStartTime();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
