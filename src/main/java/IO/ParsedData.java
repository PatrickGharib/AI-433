package IO;

import DataClass.*;
import java.util.LinkedHashSet;

public class ParsedData
{
    //So everything the parser reads will become objects and popped into these lists.
    //Some notes: The parser checks the validity, assume the objects are initialized correctly and there's no course numbers less than 0 etc.
    //Your code will not start until the parser is completely finished, the below data is a total collection of the data given.
    //DataClass.Course is parent of DataClass.Lab. All the objects just return courses. If you want to know whether that 'course': C is a lab use (C instanceof DataClass.Lab) to check.

    public static LinkedHashSet<CourseSlot>                 COURSE_SLOTS        = new LinkedHashSet<CourseSlot>();
    public static LinkedHashSet<LabSlot>                    LAB_SLOT            = new LinkedHashSet<LabSlot>();
    public static LinkedHashSet<Section>                    COURSES             = new LinkedHashSet<Section>();
    public static LinkedHashSet<Lab>                        LABS                = new LinkedHashSet<Lab>();
    public static LinkedHashSet<NotCompatibleCoursePair>    NOT_COMPATIBLE      = new LinkedHashSet<NotCompatibleCoursePair>();
    public static LinkedHashSet<UnwantedCourseTime>         UNWANTED            = new LinkedHashSet<UnwantedCourseTime>();
    public static LinkedHashSet<PreferredCourseTime>        PREFERENCES         = new LinkedHashSet<PreferredCourseTime>();
    public static LinkedHashSet<PreferredCoursePair>        PAIR                = new LinkedHashSet<PreferredCoursePair>();
    public static LinkedHashSet<PreAssignedCourseTime>      PARTIAL_ASSIGNMENTS = new LinkedHashSet<PreAssignedCourseTime>();
}
