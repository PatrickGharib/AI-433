package DataClass;

import java.util.*;

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

    public Slot(Day day, float startTime, int max, int min, List<Float> validTimes) {
        this.day = day;
        this.startTime = startTime;
        this.max = max;
        this.min = min;

        if (!validTimes.contains(startTime)) throw new IllegalArgumentException("The provided start time is invalid.");
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
        if(this instanceof LabSlot)
        {
            if(s instanceof LabSlot)
                if(!getDay().equals(s.getDay())) return false;
            else
            {
                if((day.equals(Day.MO)||day.equals(Day.FR)) && !s.getDay().equals(Day.MO)) return false;
                if(day.equals(Day.TU) && !s.getDay().equals(Day.TU)) return false;
            }
        }
        else
        {
            if(s instanceof LabSlot)
            {
                if((s.getDay().equals(Day.MO)||s.getDay().equals(Day.FR)) && day.equals(Day.MO)) return false;
                if(s.getDay().equals(Day.TU) && !getDay().equals(Day.TU)) return false;
            }
            else
                if(day != s.getDay()) return false;
        }

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
