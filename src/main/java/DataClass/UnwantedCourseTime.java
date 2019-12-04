package DataClass;

import java.util.Objects;

public class UnwantedCourseTime
{
    private Course course;
    private Slot slot;

    public UnwantedCourseTime(Course course, Slot slot)
    {
        this.course = course;
        this.slot = slot;
    }

    public Course getCourse() { return course; }

    public Slot getSlot() { return slot; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnwantedCourseTime that = (UnwantedCourseTime) o;
        return getCourse().equals(that.getCourse()) &&
                getSlot().equals(that.getSlot());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourse(), getSlot());
    }

    @Override
    public String toString() {
        return "UnwantedCourseTime{" +
                "course=" + course +
                ", slot=" + slot +
                '}';
    }
}
