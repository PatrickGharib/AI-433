package DataClass;

import java.util.Objects;

public abstract class Course {
    protected String courseName;
    protected int courseNumber;

    public Course(String courseName, int courseNumber){
      this.courseName = courseName;
      this.courseNumber = courseNumber;
    }


    public String getCourseName() {
        return courseName;
    }

    public int getCourseNumber() {
        return courseNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course section = (Course) o;
        return getCourseNumber() == section.getCourseNumber() &&
                getCourseName().equals(section.getCourseName());
    }

    @Override
    public int hashCode() {
        return 87 * Objects.hash(getCourseName(), getCourseNumber());
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", courseNumber=" + courseNumber +
                '}';
    }
}
