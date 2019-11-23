package DataClass;

public abstract class SlotAbs {
    //May want to change startTime to a different variable (pair of hour,minute)
    protected String day;
    protected String startTime;

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public SlotAbs(String day, String startTime) {
        this.day = day;
        this.startTime = startTime;
    }
}
