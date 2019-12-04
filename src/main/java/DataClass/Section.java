package DataClass;

import java.util.Objects;

public class Section extends Course{

    private int lecNum;

    public Section(String courseName, int courseNumber, int lecNum){
        super(courseName, courseNumber);
        this.lecNum = lecNum;
    }

    public int getLecNum() {
        return lecNum;
    }

    public String getSection() {
        return String.format("%s %d", courseName, courseNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Section course = (Section) o;
        return getLecNum() == course.getLecNum();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLecNum());
    }

    @Override
    public String toString() {
        return "Section{" +
                "lecNum=" + lecNum +
                ", courseName='" + courseName + '\'' +
                ", courseNumber=" + courseNumber +
                '}';
    }
}
