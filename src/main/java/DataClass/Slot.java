package DataClass;

import java.util.Objects;

public class Slot extends SlotAbs {

    public Slot(String day, String startTime, int courseMax, int courseMin) {
        super(day, startTime, courseMax, courseMin);
        endCalc(this.startHour,this.startMin);
    }

    private void endCalc(int shour,int smin){
        if(this.day.equals("TU")){
            if(smin == 30){
                this.endHour = shour + 2;
                this.endMin = 0;
                return;
            }
            this.endHour = shour + 1;
            this.endMin = 30;
            return;
        }
        this.endHour = shour + 1;
        this.endMin = smin;
    }

    public int getCourseMax() {
        return max;
    }

    public int getCourseMin() {
        return min;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Slot slot = (Slot) o;
        return max == slot.max &&
                min == slot.min;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), max, min);
    }
}
