public class RegexStrings {
    //    String COURSE_MAX = ("[\\s]*([\\d]+)[\\s]*");
//    String COURSE_MIN = ("[\\s]*([\\d]+)[\\s]*");
//    String LAB_MAX = COURSE_MAX;
//    String LAB_MIN = COURSE_MIN;
//    String VALID_COURSE_NAME = ("[\\s]*(CPSC|SENG)[\\s]*");
//    String LEC_VALID = ("[\\s]*(LEC)[\\s]*");
//    String TUT_LAB_VALID = ("[\\s]*(LAB|TUT)[\\s]*");
//    String TUT_LAB_NUMBER = ("[\\s]*([\\d]+)[\\s]*");
//    String COMMA = ("[\\s]*,[\\s]*");

    public final String COURSE_VALID_DAY = ("[\\s*](MO|TU)[\\s]*");
    public final String LAB_VALID_DAY = ("[\\s*](MO|TU|FR)([\\s]*)");
    public final String TIMES = "[\\s*](((([0|1]?)[\\d])|([2][0-3])):([0-5][\\d]))[\\s]*";
    public final String COURSE_SLOTS = ("([\\s]*(MO|TU)[\\s]*,[\\s]*(([0|1]?[\\d]|[2][0-3]):([0-5][\\d]))[\\s]*,[\\s]*([\\d]+)[\\s]*,[\\s]*([\\d]+)[\\s]*)");
    public final String LAB_SLOTS = ("([\\s]*(MO|TU|FR)[\\s]*,[\\s]*(([0|1]?[\\d]|[2][0-3]):([0-5][\\d]))[\\s]*,[\\s]*([\\d]+)[\\s]*,[\\s]*([\\d]+)[\\s]*)");
    public final String COURSES = ("([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(LEC)[\\s]*([\\d]{2})[\\s]*)");
    public final String LABS = ("([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(LEC)[\\s]*([\\d]{2})[\\s]*(TUT|LAB)[\\s]*([\\d]{2})[\\s]*)|" +
            "(([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(TUT|LAB)[\\s]*([\\d]{2}))[\\s]*)");

    public final String NOTCOMPATABLE = COURSES + "|" + LABS + "," + COURSES + "|" + LABS;
    public final String UNWANTED = "(" + COURSES + "," + COURSE_VALID_DAY + ")|(" + LABS + "," + LAB_VALID_DAY + ")" + "," + TIMES;
    public final String PREFERENCES_C = COURSE_VALID_DAY + "," + TIMES + "," + COURSES + "[\\s]*([\\d]+)[\\s]*";
    public final String PREFERENCES_L = COURSE_VALID_DAY + "," + TIMES + "," + COURSES + "[\\s]*([\\d]+)[\\s]*";
    public final String PAIR = NOTCOMPATABLE;
    public final String PARTIALASSIGNMENT = UNWANTED;


}
