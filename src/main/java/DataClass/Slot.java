package DataClass;

import com.google.common.base.Objects;

import java.util.*;

public abstract class Slot {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Float.compare(slot.startTime, startTime) == 0 &&
                Float.compare(slot.endTime, endTime) == 0 &&
                max == slot.max &&
                min == slot.min &&
                day == slot.day;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(day, startTime, endTime, max, min);
    }

    public enum Day
    {
        MO,
        TU,
        FR
    }

    protected Day day;
    protected float startTime;
    protected float endTime;

    protected int max;
    protected int min;

    public Slot(Day day, float startTime, int max, int min, List<Float> validTimes) {
        this.day = day;
        this.startTime = startTime;
        this.max = max;
        this.min = min;

        if (!validTimes.contains(startTime)) throw new IllegalArgumentException("The provided start time is invalid: " + startTime);// +":" + (int)((startTime-(int)startTime)*60)+ "\n");
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

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
        if (s == null) return false;
        if(this instanceof LabSlot)
        {
            if(s instanceof LabSlot){
                //System.out.println("A");
                if(!getDay().equals(s.getDay())) {
                    //System.out.println("Fail 1-2");
                    return false;
                }
            }
            else
            {
                //System.out.println("B");
                if((day.equals(Day.MO)||day.equals(Day.FR)) && !s.getDay().equals(Day.MO)) {
                    //System.out.println("Fail 1-2");
                    return false;
                }
                if(day.equals(Day.TU) && !s.getDay().equals(Day.TU)) {
                    //System.out.println("Fail 1-2");
                    return false;
                }
            }
        }
        else
        {
            if(s instanceof LabSlot)
            {
                //System.out.println("C");
                if((s.getDay().equals(Day.MO)||s.getDay().equals(Day.FR)) && day.equals(Day.MO)) {
                    //System.out.println("Fail 1-1");
                    return false;
                }
                if(s.getDay().equals(Day.TU) && !day.equals(Day.TU)) {
                    //System.out.println("Fail 1-2");
                    return false;
                }
            }
            else
                //System.out.println("D");
                if(day != s.getDay()) {
                    //System.out.println("Fail 1-3");
                    return false;
                }
        }

        return getEndTime() > s.getStartTime() && s.getEndTime() > getStartTime();
    }




    @Override
    public String toString() {
        return "Slot{" +
                "day=" + day +
                ", startTime=" + startTime +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}
