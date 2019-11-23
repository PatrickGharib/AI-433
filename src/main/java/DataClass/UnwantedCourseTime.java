package DataClass;

public class UnwantedCourseTime
{
    private Course course;
    private SlotAbs slot;

    public UnwantedCourseTime(Course course, SlotAbs slot)
    {
        this.course = course;
        this.slot = slot;
    }

    public Course getCourse() { return course; }

    public SlotAbs getSlot() { return slot; }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof PreferredCourseTime)
        {
            PreferredCourseTime them = (PreferredCourseTime) o;
            return getCourse().equals(them.getCourse()) && getSlot().equals(them.getSlot()) && getSlot().equals(them.getSlot());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
