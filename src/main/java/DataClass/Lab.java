package DataClass;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;

import IO.*;

public class Lab extends Course {
    private String tutLab;
    private int tutLabNum;
    private LinkedHashSet<Section> sections;

    //Lab belongs to specific course section
    public Lab(String courseName, int courseNumber, String tutLab, int tutLabNum, int lecNum) {
        super(courseName, courseNumber);
        this.tutLab = tutLab;
        this.tutLabNum = tutLabNum;
        this.sections = new LinkedHashSet<>(Collections.singletonList(new Section(courseName, courseNumber, lecNum)));
    }

    //Lab belongs to all course sections
   public Lab(String courseName, int courseNumber, String tutLab, int tutLabNum)
   {
       super(courseName, courseNumber);
       this.tutLab = tutLab;
       this.tutLabNum = tutLabNum;
       LinkedHashSet<Section> temp = new LinkedHashSet<Section>();
       for (Section section : ParsedData.COURSES)
       {
           if(section.getCourseName().equals(courseName) && section.getCourseNumber() == courseNumber)
                temp.add(section);
       }
       this.sections = temp;
   }

    public String getTutLab() {
        return tutLab;
    }

    public int getTutLabNum() {
        return tutLabNum;
    }

    public LinkedHashSet<Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lab lab = (Lab) o;
        return getTutLabNum() == lab.getTutLabNum() &&
                getTutLab().equals(lab.getTutLab());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTutLab(), getTutLabNum());
    }

    @Override
    public String toString() {
        return "Lab{" +
                "tutLab='" + tutLab + '\'' +
                ", tutLabNum=" + tutLabNum +
                ", sections=" + sections +
                ", courseName='" + courseName + '\'' +
                ", courseNumber=" + courseNumber +
                '}';
    }
}

