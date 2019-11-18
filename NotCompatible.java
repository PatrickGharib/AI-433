

public class NotCompatible {
    private Course course1;
    private Course course2;
    private Lab lab1;
    private Lab lab2;


    public NotCompatible(Course course1, Course course2) {
        this.course1 = course1;
        this.course2 = course2;
    }

    public NotCompatible(Course course1, Lab lab1) {
        this.course1 = course1;
        this.lab1 = lab1;
    }

    public NotCompatible(Lab lab1, Lab lab2) {
        this.lab1 = lab1;
        this.lab2 = lab2;
    }

    public boolean isLL() {
        if (course1 != null) {
            return false;
        } else return true;
    }
    public boolean isCC(){
        if (lab1 != null){
            return false;
        }else return true;
    }
    public boolean isCL{
        if (course2!=null && lab2 != null)
            return false;
        else return true;
    }

}
