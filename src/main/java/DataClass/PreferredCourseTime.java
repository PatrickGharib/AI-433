package DataClass;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreferredCourseTime that = (PreferredCourseTime) o;
        return getPreferenceVal() == that.getPreferenceVal() &&
                getSlot().equals(that.getSlot()) &&
                getCourse().equals(that.getCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSlot(), getCourse());
    }

    @Override
    public String toString() {
        return "PreferredCourseTime{" +
                "slot=" + slot +
                ", course=" + course +
                ", preferenceVal=" + preferenceVal +
                '}';
    }
}
