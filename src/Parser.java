import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {


    public static void inputReader(String fileName) {
        List<String> zonesRead = new ArrayList<String>();
        String zone;
        FileReader fr = null;
        BufferedReader br = null;
        String line = "";
        System.out.println(fileName);
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
                if (line.trim().matches("Name:") && !zonesRead.contains(line.trim()) && zonesRead.isEmpty()) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    line = readName(br, line);
                } //System.exit(0);

                if (line.trim().matches("Course slots:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 1) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    line = readCourseSlot(br,line);

                } // System.exit(0);

                if (line.trim().matches("Lab slots:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 2) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    line = readLabSlot(br, line);
                }

                if (line.trim().matches("Courses:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 3) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    line = readCourse(br, line);
                }

                if (line.trim().matches("Labs:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 4) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                }
                if (line.trim().matches("Not compatible:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 5) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                }

                if (line.trim().matches("Unwanted:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 6) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                }

                if (line.trim().matches("Preferences:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 7) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                }

                if (line.trim().matches("Pair:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 8) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                }

                if (line.trim().matches("Partial assignments:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 9) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                }


            }
            br.close();
        } catch (FileNotFoundException ex) {
            // SHOULD THIS BE ERROR WHILE PARSING
            System.out.println("File not found");
            System.exit(0);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readCourseSlot(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Lab slots:";
        try {
            while(!(line = br.readLine()).trim().matches(exitString)){
                if (line.matches("[\\s]*")) {lastLine = line; continue;}
                if (!line.matches(RegexStrings.COURSE_SLOTS)){
                    System.out.println("Parsing error: Could not parse File in course slot");
                    System.out.println(line);
                    System.exit(0);
                }
                if (line.matches(RegexStrings.COURSE_SLOTS)){
                    System.out.println(line);
                }


                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*"))
                {System.out.println("Parsing error: Could not parse File in course slot(no space)");System.exit(0); }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
    private static String readLabSlot(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Courses:";
        try {
            while(!(line = br.readLine()).trim().matches(exitString)){
                if (line.matches("[\\s]*")) {lastLine = line; continue;}
                if (!line.matches(RegexStrings.LAB_SLOTS)){
                    System.out.println("Parsing error: Could not parse File in Lab slot");
                    System.out.println(line);
                    System.exit(0);
                }
                if (line.matches(RegexStrings.LAB_SLOTS)){
                    System.out.println(line);
                }


                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*"))
                {System.out.println("Parsing error: Could not parse File in Lab slot(no space)");System.exit(0); }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
    private static String readCourse(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Labs:";
        try {
            while(!(line = br.readLine()).trim().matches(exitString)){
                if (line.matches("[\\s]*")) {lastLine = line; continue;}
                if (!line.matches(RegexStrings.COURSES)){
                    System.out.println("Parsing error: Could not parse File in Courses");
                    System.out.println(line);
                    System.exit(0);
                }
                if (line.matches(RegexStrings.COURSES)){
                    System.out.println(line);
                }


                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*"))
                {System.out.println("Parsing error: Could not parse File in Courses(no space)");System.exit(0); }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    private static String readName(BufferedReader br, String line) {
        try {
            int namesFound = 0;
            String lastLine = "";
            String exitString = "Course slots:";
            while (!(line = br.readLine()).trim().matches(exitString)) {
                if (line.matches("[\\s]*")) {lastLine = line; continue;}
                if (line.matches("[\\s]*[\\S]+[\\s]*")) namesFound++;
                else {System.out.println("Parsing error: Could not parse File in Name");/*System.exit(0);*/}
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*") || namesFound != 1)
                {System.out.println("Parsing error: Could not parse File in Name(no space)");/*System.exit(0);*/ }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static void main(String[] args) {
        //String fileName = args[0];
        //Scanner in = new Scanner(System.in);String fileName = in.nextLine();
        for (String x : args){

            inputReader(x);
            System.out.println("_______________________________");
        }
    }
}