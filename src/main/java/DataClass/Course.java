package DataClass;

import java.util.Objects;

public class Course {
    private String courseName;
    private int courseNumber;
    private String lec;
    private int lecNum;

    public Course(String courseName, int courseNumber, String lec, int lecNum){
      this.courseName = courseName;
      this.courseNumber = courseNumber;
      this.lec = lec;
      this.lecNum = lecNum;
    }


    public String getCourseName() {
        return courseName;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getLec() {
        return lec;
    }

    public int getLecNum() {
        return lecNum;
    }

    public String getSection() {
        return String.format("%s %d", courseName, courseNumber);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Course)
        {
            Course them = (Course) o;
            String checkMe = getCourseName() + getCourseNumber() + getLec() + getLecNum();
            String checkThem = them.getCourseName() + them.getCourseNumber() + them.getLec() + them.getLecNum();
            return checkMe.equals(checkThem);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, courseNumber, lec, lecNum);
    }
}
