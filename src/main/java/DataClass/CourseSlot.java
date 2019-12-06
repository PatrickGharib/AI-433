package DataClass;

import java.util.Arrays;
import java.util.Collections;

public class CourseSlot extends Slot {

    public CourseSlot(Day day, float startTime, int courseMax, int courseMin) {
        super(validateName(day), startTime, courseMax, courseMin,
                day.equals(Day.MO)||day.equals(Day.FR) ? Arrays.asList(8f,9f,10f,11f,12f,13f,14f,15f,16f,17f,18f,19f,20f) :
                day.equals(Day.TU) ? Arrays.asList(8f, 9.5f, 11f, 12.5f, 14f, 15.5f, 17f, 18.5f) :
                        Collections.emptyList());
        endCalc();
    }

    public CourseSlot(String day, float startTime, int courseMax, int courseMin) {
        this(validateName(Day.valueOf(day)), startTime, courseMax, courseMin);
    }

    private static Day validateName(Day day)
    {
        if(day == Day.FR) throw new IllegalArgumentException("Courses may not be on a Friday");
        return day;
    }

    private void endCalc()
    {
        endTime = day.equals(Day.TU) ? startTime + 1.5f : startTime + 1f;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof CourseSlot;
    }

}
