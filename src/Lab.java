
public class Lab extends Course {
    private String tutLab;
    private int tutLabNum;

    public Lab(String courseName, int courseNumber, String type, int lecNum, String tutLab, int tutLabNum) {
        super(courseName, courseNumber, type, lecNum);
        this.tutLab = tutLab;
        this.tutLabNum = tutLabNum;
    }
   public Lab(String courseName, int courseNumber, String type, int lecNum) {
        super(courseName, courseNumber, type, lecNum);
    }

    public String getTutLab() {
        return tutLab;
    }

    public int getTutLabNum() {
        return tutLabNum;
    }
}

