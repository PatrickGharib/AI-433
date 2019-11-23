package DataClass;

import java.util.Objects;

public class LabSlot extends SlotAbs {
    private int labMax;
    private int labMin;

    public LabSlot(String day, String startTime, int labMax, int labMin) {
        super(day, startTime);
        this.labMax = labMax;
        this.labMin = labMin;
        endCalc(this.startHour,this.startMin);
    }
    private void endCalc(int shour,int smin){
        if(this.day.equals("FR")){
            this.endHour = shour + 2;
            this.endMin = smin;
        }
        this.endHour = shour + 1;
        this.endMin = smin;
    }

    public int getLabMax() {
        return labMax;
    }

    public int getLabMin() {
        return labMin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LabSlot labSlot = (LabSlot) o;
        return labMax == labSlot.labMax &&
                labMin == labSlot.labMin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), labMax, labMin);
    }
}

