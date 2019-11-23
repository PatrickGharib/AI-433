package DataClass;

import java.util.Objects;

public class PreAssignedCourseTime
{
    private Course course;
    private SlotAbs slot;

    public PreAssignedCourseTime(Course course, SlotAbs slot)
    {
        this.course = course;
        this.slot = slot;
    }

    public Course getCourse() { return course; }

    public SlotAbs getSlot() { return slot; }

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