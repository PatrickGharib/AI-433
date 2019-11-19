package DataClass;

public class PreferredCourseTime
{
    private Slot slot;
    private Course course;
    private int preferenceVal;

    public PreferredCourseTime(Slot slot, Course course, int preferenceVal)
    {
        this.slot = slot;
        this.course = course;
        this.preferenceVal =  preferenceVal;
    }

    public Slot getSlot() { return slot; }

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
}
