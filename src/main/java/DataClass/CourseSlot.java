package DataClass;

public class CourseSlot extends Slot {

    public CourseSlot(Day day, float startTime, int courseMax, int courseMin) {
        super(validateName(day), startTime, courseMax, courseMin);
        endCalc(getStartTime());
    }

    public CourseSlot(String day, float startTime, int courseMax, int courseMin) {
        this(validateName(Day.valueOf(day)), startTime, courseMax, courseMin);
    }

    private static Day validateName(Day day)
    {
        if(day == Day.FR) throw new IllegalArgumentException("Courses may not be on a Friday");
        return day;
    }

    private void endCalc(float startTime){
        if(this.day.equals(Day.TU)){
            if(startTime % 0.5f == 0f){
                this.endTime = startTime + 2f;
                return;
            }
            this.endTime = startTime + 1.5f;
            return;
        }
        this.endTime = startTime + 1f;
    }
}
