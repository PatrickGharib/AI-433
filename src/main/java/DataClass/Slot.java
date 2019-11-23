package DataClass;

import java.util.Objects;

public class Slot extends SlotAbs {
    private int courseMax;
    private int courseMin;

    public Slot(String day, String startTime, int courseMax, int courseMin) {
        super(day, startTime);
        this.courseMax = courseMax;
        this.courseMin = courseMin;
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
        return courseMax;
    }

    public int getCourseMin() {
        return courseMin;
    }


}
