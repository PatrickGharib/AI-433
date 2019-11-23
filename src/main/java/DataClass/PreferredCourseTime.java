package DataClass;

public class PreferredCourseTime
{
    private SlotAbs slot;
    private Course course;
    private int preferenceVal;

    public PreferredCourseTime(SlotAbs slot, Course course, int preferenceVal)
    {
        this.slot = slot;
        this.course = course;
        this.preferenceVal =  preferenceVal;
    }

    public SlotAbs getSlot() { return slot; }

    public Course getCourse() { return course; }

    public int getPreferenceVal() { return preferenceVal; }

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
