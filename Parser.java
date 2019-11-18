import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {


//    String COURSE_MAX = ("[\\s]*([\\d]+)[\\s]*");
//    String COURSE_MIN = ("[\\s]*([\\d]+)[\\s]*");
//    String LAB_MAX = COURSE_MAX;
//    String LAB_MIN = COURSE_MIN;
//    String VALID_COURSE_NAME = ("[\\s]*(CPSC|SENG)[\\s]*");
//    String LEC_VALID = ("[\\s]*(LEC)[\\s]*");
//    String TUT_LAB_VALID = ("[\\s]*(LAB|TUT)[\\s]*");
//    String TUT_LAB_NUMBER = ("[\\s]*([\\d]+)[\\s]*");
//    String COMMA = ("[\\s]*,[\\s]*");

    private final String COURSE_VALID_DAY = ("[\\s*](MO|TU)[\\s]*");
    private final String LAB_VALID_DAY = ("[\\s*](MO|TU|FR)([\\s]*)");
    private final String TIMES = "[\\s*](((([0|1]?)[\\d])|([2][0-3])):([0-5][\\d]))[\\s]*";
    private final String COURSE_SLOTS = ("([\\s]*(MO|TU)[\\s]*,[\\s]*(([0|1]?[\\d]|[2][0-3]):([0-5][\\d]))[\\s]*,[\\s]*([\\d]+)[\\s]*,[\\s]*([\\d]+)[\\s]*)");
    private final String LAB_SLOTS = ("([\\s]*(MO|TU|FR)[\\s]*,[\\s]*(([0|1]?[\\d]|[2][0-3]):([0-5][\\d]))[\\s]*,[\\s]*([\\d]+)[\\s]*,[\\s]*([\\d]+)[\\s]*)");
    private final String COURSES = ("([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(LEC)[\\s]*([\\d]{2})[\\s]*)");
    private final String LABS = ("([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(LEC)[\\s]*([\\d]{2})[\\s]*(TUT|LAB)[\\s]*([\\d]{2})[\\s]*)|" +
            "(([\\s]*(CPSC|SENG)[\\s]*([\\d]{3})[\\s]*(TUT|LAB)[\\s]*([\\d]{2}))[\\s]*)");

    private final String NOTCOMPATABLE = COURSES + "|" + LABS + "," + COURSES + "|" + LABS;
    private final String UNWANTED = "(" + COURSES + "," + COURSE_VALID_DAY + ")|(" + LABS + "," + LAB_VALID_DAY + ")" + "," + TIMES;
    private final String PREFERENCES_C = COURSE_VALID_DAY + "," + TIMES + "," + COURSES + "[\\s]*([\\d]+)[\\s]*";
    private final String PREFERENCES_L = COURSE_VALID_DAY + "," + TIMES + "," + COURSES + "[\\s]*([\\d]+)[\\s]*";
    private final String PAIR = NOTCOMPATABLE;
    private final String PARTIALASSIGNMENT = UNWANTED;


    public static void inputReader(String fileName) {
        String zone;
        String[] readZones = new String[10];
        boolean validZone;
        
        FileReader fr = null;
        BufferedReader br = null;
        String line = "";

        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                // creates a matcher object with a specific pattern that it looks for
                // this is how most of the error checking is done
                //Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
                // looks for name header along with an infinite amount of white space that
                // follows
                // there can be no white space before though
                zone = line;
                switch(zone){
                    case (:
                }
                if();
                {
                    zone = line;
                    readName();
                }

            }
                br.close();
            } catch(FileNotFoundException ex){
                // SHOULD THIS BE ERROR WHILE PARSING
                System.out.println("File not found");
                System.exit(0);
                return;
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    public static void main (String[]args){
        String fileName = args[0];
        inputReader(fileName);
    }
}