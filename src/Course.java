public class Course {
    private String courseName;
    private int courseNumber;
    private String lec;
    private int lecNum;


    public Course(String courseName, int courseNumber, String lec, int lecNum){
      this.courseName = courseName;
      this.courseNumber = courseNumber;
      this.lec = lec;
      this.lecNum = lecNum;
    }


    public String getCourseName() {
        return courseName;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public String getLec() {
        return lec;
    }

    public int getLecNum() {
        return lecNum;
    }
}
