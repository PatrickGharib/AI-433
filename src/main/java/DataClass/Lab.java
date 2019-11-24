package DataClass;

import java.util.LinkedHashSet;
import java.util.Objects;

public class Lab extends Course {
    private String tutLab;
    private int tutLabNum;
    private LinkedHashSet<Course> sections;

    public Lab(String courseName, int courseNumber, String type, int lecNum, String tutLab, int tutLabNum, LinkedHashSet<Course> sections) {
        super(courseName, courseNumber, type, lecNum);
        this.tutLab = tutLab;
        this.tutLabNum = tutLabNum;
        this.sections = sections;
    }
   public Lab(String courseName, int courseNumber, String type, int lecNum) {
        super(courseName, courseNumber, type, lecNum);
    }

    public String getTutLab() {
        return tutLab;
    }

    public int getTutLabNum() {
        return tutLabNum;
    }

    public LinkedHashSet<Course> getSections() {
        return sections;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lab lab = (Lab) o;
        return getTutLabNum() == lab.getTutLabNum() &&
                getTutLab().equals(lab.getTutLab());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTutLab(), getTutLabNum());
    }
}

