package DataClass;

public class CourseSlot extends Slot {

    public CourseSlot(Day day, String startTime, int courseMax, int courseMin) {
        super(validateName(day), startTime, courseMax, courseMin);
        endCalc(this.startHour,this.startMin);
    }

    private static Day validateName(Day day)
    {
        if(day == Day.FR) throw new IllegalArgumentException("Courses may not be on a Friday");
        return day;
    }

    public CourseSlot(String day, String startTime, int courseMax, int courseMin) {
        super(validateName(Day.valueOf(day)), startTime, courseMax, courseMin);
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
