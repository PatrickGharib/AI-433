package DataClass;

import com.google.common.base.Objects;

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
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", courseNumber=" + courseNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseNumber == course.courseNumber &&
                Objects.equal(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(courseName, courseNumber);
    }
}
