package DataClass;

import java.util.Objects;

public class Lab extends Course {
    private String tutLab;
    private int tutLabNum;
    private Course course;              //Associated course TODO

    public Lab(String courseName, int courseNumber, String type, int lecNum, String tutLab, int tutLabNum) {
        super(courseName, courseNumber, type, lecNum);
        this.tutLab = tutLab;
        this.tutLabNum = tutLabNum;
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

    public Course getCourse(){ return course; }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Lab)
        {
            Lab them = (Lab) o;
            if (!getTutLab().equals(them.getTutLab()) || getTutLabNum() != them.getTutLabNum())
                return false;
            return super.equals(o);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tutLab, tutLabNum);
    }

}

