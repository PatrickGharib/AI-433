import DataClass.*;
import DataClass.Course;


public class PSolComparatorDecorator {
        private Course a;
        private Course b;
        private int compareResult;

    public PSolComparatorDecorator(Tuple<Course,Slot> a, Tuple<Course,Slot> b) {
        this.a = a.getKey();
        this.b = b.getKey();
    }

    public int getResult() {
        compareMe(a.getCourseName(), b.getCourseName());
        if (compareResult != 0) return compareResult;

        compareMe(a.getCourseNumber(), b.getCourseNumber());
        if (compareResult != 0) return compareResult;

        compareMe(PSolsHelpers.classMnemonic(a), PSolsHelpers.classMnemonic(b));
        if (compareResult != 0) return compareResult;

        if (a instanceof Section)
            return compareSections((Section)a, (Section)b);

        return compareLabs((Lab)a, (Lab)b);
    }

    private int compareSections(Section a, Section b) {
        compareMe(a.getLecNum(), b.getLecNum());
        if (compareResult != 0) return compareResult;
        return 0;
    }

    private int compareLabs(Lab a, Lab b) {
        compareMe(PSolsHelpers.labMnemonic(a), PSolsHelpers.labMnemonic(b));
        if (compareResult != 0) return compareResult;

        compareMe(a.getTutLabNum(), b.getTutLabNum());
        if (compareResult != 0) return compareResult;

        return 0;
    }


    private void compareMe(String a, String b) {
        compareResult =  a.compareTo(b);
    }

    private void compareMe(int a, int b) {
        compareResult = a - b;
    }
}