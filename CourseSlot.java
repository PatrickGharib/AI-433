public class CourseSlot {
    private String day;
    private String startTime;
    private int courseMax;
    private int courseMin;
    public CourseSlot(String day,String startTime,int courseMax,int courseMin){
        this.day = day;
        this.startTime = startTime;
        this.courseMax = courseMax;
        this.courseMin = courseMin;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getCourseMax() {
        return courseMax;
    }

    public int getCourseMin() {
        return courseMin;
    }
}
