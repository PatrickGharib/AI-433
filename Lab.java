
public class Lab {
    private String labName;
    private int labNumber;
    private String lab;
    private int labNum;
    public Lab(String courseName, int courseNumber, String lab, int lecNum){
        this.labName = courseName;
        this.labNumber = courseNumber;
        this.lab = lab;
        this.labNum = lecNum;
    }
    public String getLabName() {
        return labName;
    }

    public int getCourseNumber() {
        return labNumber;
    }

    public String getLec() {
        return lab;
    }

    public int getLabNum() {
        return labNum;
    }
}
