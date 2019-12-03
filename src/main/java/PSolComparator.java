import DataClass.Course;
import DataClass.Slot;

import java.util.Comparator;

public class PSolComparator implements Comparator<Tuple<Course,Slot>> {

    @Override
    public int compare(Tuple<Course, Slot> o1, Tuple<Course, Slot> o2) {
        return new PSolComparatorDecorator(o1, o2).getResult();
    }
}
