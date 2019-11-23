package DataClass;

import java.util.Objects;

public abstract class SlotAbs {
    //may change the "day" from a String to something more performant
    protected String day;
    protected int startHour;
    protected int startMin;
    protected int endHour;
    protected int endMin;


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

    public SlotAbs(String day, String startTime) {
        this.day = day;
        timeParser(startTime);
    }

    private void timeParser(String startTime){
        String[] splits = startTime.split(":");
        this.startHour = Integer.parseInt(splits[0]);
        this.startMin = Integer.parseInt(splits[1]);
    }

    //not used yet but may be useful
    public SlotAbs(String day, int startHour, int startMin) {
        this.day = day;
        this.startHour = startHour;
        this.startMin = startMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlotAbs slotAbs = (SlotAbs) o;
        return startHour == slotAbs.startHour &&
                startMin == slotAbs.startMin &&
                endHour == slotAbs.endHour &&
                endMin == slotAbs.endMin &&
                day.equals(slotAbs.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, startHour, startMin, endHour, endMin);
    }
}
