import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;






public class Parser{
    Pattern COURSE_VALID_DAY = Pattern.compile("(MO|TU)");
    Pattern LAB_VALID_DAY = Pattern.compile("(MO|TU|FR)([\\s]*)");
    Pattern TIMES = Pattern.compile("((([0|1]?)[\\d])|([2][0-3])):([0-5][\\d])");
    Pattern COURSE_MAX = Pattern.compile("([\\d]+)");
    Pattern COURSE_MIN = Pattern.compile("([\\d]+)");
    Pattern LAB_MAX = COURSE_MAX;
    Pattern LAB_MIN = COURSE_MIN;
    Pattern VALID_COURSE_NAME = Pattern.compile("(CPSC|SENG)");
    Pattern LEC_VALID = Pattern.compile("(LEC)");
    Pattern TUT_LAB_VALID = Pattern.compile("(LAB|TUT)");
    Pattern TUT_LAB_NUMBER = Pattern.compile("([\\d]+)");


    Pattern COURSE_SLOTS = Pattern.compile("([\\s]*(MO|TU)[\\s]*,[\\s]*(([0|1]?[\\d]|[2][0-3]):([0-5][\\d]))[\\s]*,[\\s]*([\\d]+)[\\s]*,[\\s]*([\\d]+)[\\s]*)");
    Pattern LAB_SLOTS = Pattern.compile("([\\s]*(MO|TU|FR)[\\s]*,[\\s]*(([0|1]?[\\d]|[2][0-3]):([0-5][\\d]))[\\s]*,[\\s]*([\\d]+)[\\s]*,[\\s]*([\\d]+)[\\s]*)");
    Pattern COURSES =  Pattern.compile("[\\s]*(CPSC|SENG)[\\s]*(\\d{3})[\\s]*(LEC)[\\s]*([\\d{2}])");
    Pattern LABS = Pattern.compile("[\\s]*(CPSC|SENG)[\\s]*(\\d{3})[\\s]*(LEC)[\\s]*(\\d{2})(TUT|LAB)[\\s]*(\\d{2}))");
    Pattern TUT = Pattern.compile("([\\s]*(CPSC|SENG)[\\s]*(\\d{3})[\\s]*(TUT)[\\s]*(\\d{2}))");
    Pattern COMMA = Pattern.compile("[\\s]*,[\\s]*";
   // Pattern UNWANTED = COURSES+COMMA+COURSE_VALID_DAY+COMMA+TIMES;


    public void inputReader(String fileName) {
        int[] sectionRead = new int[10];
        // make buffered reader
        FileReader fr = null;
        BufferedReader br = null;
        // current line the parser is reading
        String line = "";
        // constraint counter which is used to count the headers
        int constraintCounter = 0;
        // make Forbidden machine element object
//        try {
//            // open up the file
//            fr = new FileReader(fileName);
//            // make a new buffered reader in the file
//            br = new BufferedReader(fr);
//            // read till the file till nothing left
//            while ((line = br.readLine()) != null) {
//                // uses regular expressions to catch empty lines [\\s]* checks for an infinite amount of white space
//                // creates a matcher object with a specific pattern that it looks for
//                // this is how most of the error checking is done
//                Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
//                // if line empty skip
//                if (emptyCheck.matches())
//                    continue;
//                // looks for name header along with an infinite amount of white space that
//                // follows
//                // there can be no white space before though
//                if (line.matches("Name:[\\s]*")) {
//                    constraintCounter++;
//                    // TODO this should just check that there is a name for the file
//                }
//                if (line.matches("Course slots:[\\s]*")) {
//                    constraintCounter++;
//                }
//
//                if (line.matches("Lab slots:[\\s]*")) {
//                    constraintCounter++;
//                    // passing in corresponding object for the constraint
//
//                }
//
//                if (line.matches("Courses:[\\s]*")) {
//                    constraintCounter++;
//
//                }
//
//                if (line.matches("Labs:[\\s]*")) {
//                    constraintCounter++;
//
//                }
//
//                if (line.matches("Not compatible:[\\s]*")) {
//                    constraintCounter++;
//
//                }
//
//                //System.out.println(line);
//            }
//            // checks constraint counter to see if = 6 if not err.
//            if (constraintCounter != 6) {
//                Shell.constructFileOutPut(1);
//            }
//            // System.out.println(constraintCounter);
//            br.close();
//        } catch (FileNotFoundException ex) {
//            // SHOULD THIS BE ERROR WHILE PARSING
//            System.out.println("File not found");
//            System.exit(0);
//            return;
//
//        } catch (IOException e) {
//            System.out.println("bitchin'");
//            Shell.constructFileOutPut(1);
//        }

        // try catch
        try {
            constraintCounter = 0;
            // open up the file
            fr = new FileReader(fileName);
            // make a new buffered reader in the file
            br = new BufferedReader(fr);
            // read till the file till nothing lef
            while ((line = br.readLine()) != null) {
                // uses regular expressions to catch empty lines [\\s]* checks for an infinite
                // about of white space
                // creates a matcher object with a specific pattern that it looks for
                // this is how most of the error checking is done
                //Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
                // if line empty skip
                //if (emptyCheck.matches())
                    //continue;
                // System.out.println(line);
                // looks for name header along with an infinite amount of white space that
                // follows
                // there can be no white space before though
                if (line.matches("^Name:[\\s]*")) {
                    if (!nameRead) {
                        System.out.println(line);
                        line = readName(br, line);
                        constraintCounter++;

                    } else {
                        break;
                    }
                }
                if (line.matches("Course slots:[\\s]*")) {
                    if (nameRead) {
                        constraintCounter++;
                        // passes the buffered reader(by reference thats why you don't need to return it
                        // current line is also passed into the method so that it can continue where it
                        // left off.
                        // the methods return the current line so that it can continue checking for the
                        // next constraint header
                        System.out.println(line);
                        courseRead = true;
                        //line = readForcedPartialAssignmet(br, line);
                        System.out.println(line);
                    } else {
                        break;
                    }
                }

                if (line.matches("Lab slots:[\\s]*")) {
                    if (courseRead) {

                        constraintCounter++;
                        // passing in corresponding object for the constraint
                        //line = readForbiddenMachine(br, fmElement, line);
                        System.out.println(line);
                        labSlotRead = true;
                    } else {
                        break;
                    }

                }

                if (line.matches("Courses:[\\s]*")) {
                    if (labSlotRead) {
                        constraintCounter++;
                        //line = readToNearTask(br, tntElement, line);
                        System.out.println(line);
                        tntRead = true;
                    } else {
                        break;
                    }
                }

                if (line.matches("Labs:[\\s]*")) {
                    if (tntRead) {
                        constraintCounter++;
                        //line = readMachinePenalties(br, line);
                        mpRead = true;
                        System.out.println(line);
                    } else {
                        break;
                    }
                }

                if (line.matches("Not compatible:[\\s]*")) {
                    if (mpRead)
                        constraintCounter++;
                    //line = readToNearPenalities(br, tnpElement, line);
                    tnpRead = true;
                } else {
                    break;
                }

                if (line.matches("Not compatible:[\\s]*")){

                }

            }
            // checks constraint counter to see if = 6 if not err.
            if (constraintCounter != 6) {
               // Shell.constructFileOutPut(1);
            }
            // System.out.println(constraintCounter);
            br.close();
        } catch (FileNotFoundException ex) {
            // SHOULD THIS BE ERROR WHILE PARSING
            System.out.println("File not found");
            System.exit(0);
            return;

        } catch (IOException e) {

            Shell.constructFileOutPut(1);
        }
    }

    public static void main(String[] args) {

    }









}
