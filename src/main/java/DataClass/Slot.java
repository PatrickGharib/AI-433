package DataClass;

import java.util.Objects;

public abstract class Slot {
    //may change the "day" from a String to something more performant
    protected String day;
    protected int startHour;
    protected int startMin;
    protected int endHour;
    protected int endMin;

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    protected int max;
    protected int min;

    public String getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public int getEndMin() {
        return endMin;
    }

    public Slot(String day, String startTime, int max, int min) {
        this.day = day;
        timeParser(startTime);
        this.max = max;
        this.min = min;
    }

    private void timeParser(String startTime){
        String[] splits = startTime.split(":");
        this.startHour = Integer.parseInt(splits[0]);
        this.startMin = Integer.parseInt(splits[1]);
    }

    //not used yet but may be useful
    public Slot(String day, int startHour, int startMin) {
        this.day = day;
        this.startHour = startHour;
        this.startMin = startMin;
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
