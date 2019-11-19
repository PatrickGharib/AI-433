package DataClass;

public class LabSlot {

    private String day;
    private String startTime;
    private int labMax;
    private int labMin;
    public LabSlot(String day,String startTime,int courseMax,int courseMin){
        this.day = day;
        this.startTime = startTime;
        this.labMax = courseMax;
        this.labMin = courseMin;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
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
}

