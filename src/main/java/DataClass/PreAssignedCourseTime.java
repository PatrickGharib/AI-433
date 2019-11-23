package DataClass;

import java.util.Objects;

public class PreAssignedCourseTime
{
    private Course course;
    private Slot slot;

    public PreAssignedCourseTime(Course course, Slot slot)
    {
        this.course = course;
        this.slot = slot;
    }

    public Course getCourse() { return course; }

    public Slot getSlot() { return slot; }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof PreAssignedCourseTime)
        {
            PreAssignedCourseTime them = (PreAssignedCourseTime) o;
            return getCourse().equals(them.getCourse()) && getSlot().equals(them.getSlot());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, slot);
    }
}