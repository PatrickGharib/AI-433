package IO;

import java.util.regex.Pattern;

public class RegexStrings {

    //    String COURSE_MAX = ("[\\s]*([\\d]+)[\\s]*");
//    String COURSE_MIN = ("[\\s]*([\\d]+)[\\s]*");
//    String LAB_MAX = COURSE_MAX;
//    String LAB_MIN = COURSE_MIN;
//    String VALID_COURSE_NAME = ("[\\s]*(CPSC|SENG)[\\s]*");
//    String LEC_VALID = ("[\\s]*(LEC)[\\s]*");
//    String TUT_LAB_VALID = ("[\\s]*(LAB|TUT)[\\s]*");
//    String TUT_LAB_NUMBER = ("[\\s]*([\\d]+)[\\s]*");
//    String COMMA = ("([\\s]*)[\\s])*");

    public static final String COURSE_VALID_DAY = ("([\\s]*(MO|TU)[\\s]*)");
    public static final String LAB_VALID_DAY = ("([\\s]*(MO|TU|FR)[\\s]*)");
    public static final String TIMES = "([\\s]*([0|1]?[\\d]|[2][0-3]):([0-5][\\d])[\\s]*)";
    public static final String COURSE_SLOTS = ("([\\s]*(MO|TU)[\\s]*,[\\s]*([0|1]?[\\d]|[2][0-3]):([0-5][\\d])[\\s]*,[\\s]*([\\d]+)[\\s]*,[\\s]*([\\d]+)[\\s]*)");
    public static final String LAB_SLOTS = ("([\\s]*(MO|TU|FR)[\\s]*,[\\s]*([0|1]?[\\d]|[2][0-3]):([0-5][\\d])[\\s]*,[\\s]*([\\d]+)[\\s]*,[\\s]*([\\d]+)[\\s]*)");
    public static final String COURSES = ("([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(LEC)[\\s]*([\\d]{2})[\\s]*)");
    public static final String LABS = ("([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(LEC)[\\s]*([\\d]{2})[\\s]*(TUT|LAB)[\\s]*([\\d]{2})[\\s]*)");
    public static final String TUT = "([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(TUT|LAB)[\\s]*([\\d]{2})[\\s]*)";
    public static final String TUT_LABS = TUT + "|"+LABS;

    public static final String NOTCOMPATABLE_FORMAT = "("+LABS + "|" + TUT + "|" + COURSES +")"+ "," + "("+LABS + "|" + TUT + "|" + COURSES+")";
    public static final String PAIRS_GROUPS = LABS + "|" + TUT + "|" + COURSES;

    public static final String UNWANTED ="("+ LABS +"|"+TUT+ "|" + COURSES +")" +"," + "("+LAB_VALID_DAY +"|"+ COURSE_VALID_DAY+")" + "," + TIMES;
    public static final String CRS_DAY_TIME = "[\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(LEC)[\\s]*([\\d]{2})[\\s]*,[\\s]*(MO|TU)[\\s]*,[\\s]*([0|1]?[\\d]|[2][0-3]):([0-5][\\d])[\\s]*";
    public static final String LAB_DAY_TIME ="[\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(LEC)[\\s]*([\\d]{2})[\\s]*(TUT|LAB)[\\s]*([\\d]{2})[\\s]*,[\\s]*(MO|TU|FR)[\\s]*,[\\s]*([0|1]?[\\d]|[2][0-3]):([0-5][\\d])[\\s]*";
    public static final String TUT_DAY_TIME = "[\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(TUT|LAB)[\\s]*([\\d]{2})[\\s]*,[\\s]*(MO|TU|FR)[\\s]*,[\\s]*([0|1]?[\\d]|[2][0-3]):([0-5][\\d])[\\s]*";

    public static final String PREFERENCES =  LAB_VALID_DAY + "," + TIMES + "," +"("+LABS + "|" + TUT + "|" + COURSES+ ")"+","+ "[\\s]*([\\d]+)[\\s]*";
    public static final String PREFERENCES_C = COURSE_VALID_DAY + "," + TIMES + "," + COURSES +","+ "[\\s]*([\\d]+)[\\s]*";
    public static final String PREFERENCES_L = LAB_VALID_DAY + "," + TIMES + "," + LABS + ","+"[\\s]*([\\d]+)[\\s]*";
    public static final String PREFERENCES_T = LAB_VALID_DAY + "," + TIMES + "," + TUT + ","+ "[\\s]*([\\d]+)[\\s]*";
    public static final String PAIR = NOTCOMPATABLE_FORMAT;
    public static final String PARTIALASSIGNMENT = UNWANTED;


// ([\s]*(CPSC|SENG)[\s]*([\d]{3})[\s]*(LEC)[\s]*([\d]{2})[\s]*(TUT|LAB)[\s]*([\d]{2})[\s]*)|([\s]*(CPSC|SENG)[\s]*([\d]{3})[\s]*(TUT|LAB)[\s]*([\d]{2})[\s]*)|([\s]*(CPSC|SENG)[\s]*([\d]{3})[\s]*(LEC)[\s]*([\d]{2})[\s]*) this is the full not_compatable
// ([\s]*(CPSC|SENG)[\s]*([\d]{3})[\s]*(LEC)[\s]*([\d]{2})[\s]*(TUT|LAB)[\s]*([\d]{2})[\s]*)|([\s]*(CPSC|SENG)[\s]*([\d]{3})[\s]*(TUT|LAB)[\s]*([\d]{2})[\s]*)|([\s]*(CPSC|SENG)[\s]*([\d]{3})[\s]*(LEC)[\s]*([\d]{2})[\s]*),([\s]*(MO|TU|FR)[\s]*),([\s]*([0|1]?[\d]|[2][0-3]):([0-5][\d])[\s]*) this is the full regex for
}


