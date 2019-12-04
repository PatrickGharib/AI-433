package DataClass;

import java.util.Objects;

public class NotCompatibleCoursePair
{
    private Course course1;
    private Course course2;

    public NotCompatibleCoursePair(Course course1, Course course2)
    {
        this.course1 = course1;
        this.course2 = course2;
    }

    public Course getCourse1() { return course1; }

    public Course getCourse2() {
        return course2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotCompatibleCoursePair that = (NotCompatibleCoursePair) o;
        return getCourse1().equals(that.getCourse1()) &&
                getCourse2().equals(that.getCourse2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourse1(), getCourse2());
    }

    @Override
    public String toString() {
        return "NotCompatibleCoursePair{" +
                "course1=" + course1 +
                ", course2=" + course2 +
                '}';
    }
}
