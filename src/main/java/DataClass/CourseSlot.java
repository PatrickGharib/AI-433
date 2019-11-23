package DataClass;

public class CourseSlot extends Slot {

    public CourseSlot(String day, String startTime, int courseMax, int courseMin) {
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
}
