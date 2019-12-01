import DataClass.Course;
import DataClass.Slot;
import javafx.util.Pair;

import java.util.Comparator;

public class PSolComparator implements Comparator<Pair<Course,Slot>> {

    @Override
    public int compare(Pair<Course, Slot> o1, Pair<Course, Slot> o2) {
        return new PSolComparatorDecorator(o1, o2).getResult();
    }
}
