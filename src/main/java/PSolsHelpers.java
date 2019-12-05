import DataClass.Course;
import DataClass.Lab;
import DataClass.Section;

public class PSolsHelpers {

    public static int courseName(String courseName) {
        if (courseName.toLowerCase().contains("cpsc"))
            return 1;
        return 2;
    }

    public static String className(Object obj) {
        return obj.getClass().getName();
    }

    public static int classMnemonic(Course c) {
        if (c instanceof Section) return 1;
        return 2;
    }

    public static int labMnemonic(Lab l) {
        if (l.getTutLab().toLowerCase().equals("lab")) return 1;
        return 2;
    }
}

