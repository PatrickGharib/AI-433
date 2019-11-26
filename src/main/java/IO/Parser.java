package IO;

import DataClass.*;


import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 */
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
                    line = readName(br, line);
                }
                if (line.trim().matches("Course slots:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 1) {
                    zonesRead.add(line.trim());
                    line = readCourseSlot(br, line);

                }
                if (line.trim().matches("Lab slots:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 2) {
                    zonesRead.add(line.trim());
                    line = readLabSlot(br, line);
                }
                if (line.trim().matches("Courses:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 3) {
                    zonesRead.add(line.trim());
                    line = readCourse(br, line);
                }
                if (line.trim().matches("Labs:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 4) {
                    zonesRead.add(line.trim());
                    line = readLab(br, line);
                }
                if (line.trim().matches("Not compatible:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 5) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    line = readNotCompatible(br, line);
                }
                if (line.trim().matches("Unwanted:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 6) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    line = readUnwanted(br, line);

                }
                if (line.trim().matches("Preferences:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 7) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    line = readPreferences(br, line);
                }
                if (line.trim().matches("Pair:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 8) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    line = readPair(br, line);
                }
                if (line.trim().matches("Partial assignments:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 9) {
                    zonesRead.add(line.trim());
                    System.out.println(zonesRead.toString());
                    readPartialAssignment(br, line);
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


    /**
     * @param br
     * @param line
     * @return line
     * this reads the name section and throws errors if the name is not a single word or if there is no name
     * this one does nothing special
     * returns the last line before the exit string
     */
    private static String readName(BufferedReader br, String line) {
        try {
            int namesFound = 0;
            String lastLine = "";
            String exitString = "Course slots:";
            while (!(line = br.readLine()).trim().matches(exitString)) {
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (line.matches("[\\s]*[\\S]+[\\s]*")) namesFound++;
                else {
                    System.out.println("Parsing error: Could not parse File in Name");
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*") || namesFound != 1) {
                System.out.println("Parsing error: Could not parse File in Name(no space)");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
    /**
     * @param br
     * @param line
     * @return the last line read
     * reads the course slot. prints an error if the system finds an invalid format designated by the
     * regex found in IO.RegexStrings
     * <p>
     * Creates course slot objects if format is correct
     * MATCHER LEGEND
     * -----------------
     * group(2) is the Day
     * group(3) is the 24 hour time format
     * group(4) is hours
     * group(5) is minutes(up to 59min) : this gets converted to float and added to hours.
     * group(6) is coursemax
     * group(7) is coursemin
     */
    private static String readCourseSlot(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Lab slots:";
        float minuteConversion = 60f;
        Pattern pattern = Pattern.compile(RegexStrings.COURSE_SLOTS);
        try {
            while (!(line = br.readLine()).trim().matches(exitString)) {
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.COURSE_SLOTS)) {
                    System.out.println("Parsing error: Could not parse File in course slot");
                    System.out.println(line);
                    System.exit(0);
                }
                if (line.matches(RegexStrings.COURSE_SLOTS)) {

                    Matcher matcher = pattern.matcher(line);
                    matcher.matches();

                    //convert minutes to float(minutes/60)
                    float minToDecimal = Float.parseFloat(matcher.group(4)) + (Float.parseFloat(matcher.group(5)) / minuteConversion);

                    //make new course slot and add to courseSlot hashset
                    CourseSlot newCourseSlot = new CourseSlot(matcher.group(2), minToDecimal, Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(7)));
                    ParsedData.COURSE_SLOTS.add(newCourseSlot);
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in course slot(no space)");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
    /**
     * @param br
     * @param line
     * @return the last line read
     * reads the lab slot. prints an error if the system finds an invalid format designated by the
     * regex found in IO.RegexStrings
     * <p>
     * Creates lab slot objects if format is correct
     * MATCHER LEGEND
     * -----------------
     * group(2) is the Day
     * group(3) is the 24 hour time format
     * group(4) is hours
     * group(5) is minutes(up to 59min) : this gets converted to float and added to hours.
     * group(6) is labmax
     * group(7) is labmin
     */
    private static String readLabSlot(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Courses:";
        float minuteConversion = 60f;
        Pattern pattern = Pattern.compile(RegexStrings.LAB_SLOTS);
        try {
            while (!(line = br.readLine()).trim().matches(exitString)) {
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.LAB_SLOTS)) {
                    System.out.println("Parsing error: Could not parse File in Lab slot");
                    System.out.println(line);

                }
                if (line.matches(RegexStrings.LAB_SLOTS)) {

                    Matcher matcher = pattern.matcher(line);
                    matcher.matches();

                    //convert minutes to float(minutes/60)
                    float minToDecimal = Float.parseFloat(matcher.group(4)) + (Float.parseFloat(matcher.group(5)) / minuteConversion);

                    //make new course slot and add to courseSlot hashset
                    LabSlot newLabSlot = new LabSlot(matcher.group(2), minToDecimal, Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(7)));
                    ParsedData.LAB_SLOT.add(newLabSlot);
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Lab slot(no space)");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
    /**
     * @param br
     * @param line
     * @return the last line read
     * reads the course. prints an error if the system finds an invalid format designated by the
     * regex found in IO.RegexStrings
     * <p>
     * Creates course objects if format is correct
     * MATCHER LEGEND
     * -----------------
     * group(2) is Course name
     * group(3) is Course #
     * group(4) is "LEC"
     * group(5) section #
     */
    private static String readCourse(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Labs:";
        Pattern pattern = Pattern.compile(RegexStrings.COURSES);
        try {
            while (!(line = br.readLine()).trim().matches(exitString)) {
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.COURSES)) {
                    System.out.println("Parsing error: Could not parse File in Courses");
                    System.out.println(line);
                    System.exit(0);
                }
                if (line.matches(RegexStrings.COURSES)) {
                    Matcher matcher = pattern.matcher(line);
                    matcher.matches();
                    //make new course slot and add to courseSlot hashset
                    //Ignoring group(4): "LEC" as it does not contain any additional information.
                    Section newCourse = new Section(matcher.group(2), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(5)));
                    ParsedData.COURSES.add(newCourse);
                }

                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Courses(no space)");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
    /**
     * @param br
     * @param line
     * @return the last line read
     * reads the course. prints an error if the system finds an invalid format designated by the
     * regex found in IO.RegexStrings
     * <p>
     * Creates course objects if format is correct
     * MATCHER LEGEND(LAB)
     * -----------------
     * group(2) is Course name
     * group(3) is Course #
     * group(4) is "LEC"
     * group(5) section #
     * group(6) "LAB|TUT"
     * group(7) lab section #
     * <p>
     * MATCHER LEGEND(TUT)
     * -----------------
     * group(3) is Course Name
     * group(4) is Course #
     * group(5) "LAB|TUT"
     * group(6) is tutorial section
     */
    //TODO need to make sure the objects are being created properly, talk to hannah about this
    private static String readLab(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Not compatible:";
        try {
            while (!(line = br.readLine()).trim().matches(exitString)) {
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.LABS) && !line.matches(RegexStrings.TUT)) {
                    System.out.println("Parsing error: Could not parse File in labs");
                    System.out.println(line);
                    System.exit(0);
                }
                if (line.matches(RegexStrings.LABS)) {
                    Pattern pattern = Pattern.compile(RegexStrings.LABS);
                    Matcher matcher = pattern.matcher(line);
                    matcher.matches();

                    int labNumber = Integer.parseInt(matcher.group(3));
                    String labName = matcher.group(2);

                    //TODO we have to talk about how to deal with duplicates. i need help with that maybe

                    //make new Lab and add to lab set
                    Lab newLab = new Lab(labName, labNumber, matcher.group(6), Integer.parseInt(matcher.group(7)), Integer.parseInt(matcher.group(5)));
                    ParsedData.LABS.add(newLab);
                }

                //TODO check with hannah if i need to also check sections here
                if (line.matches(RegexStrings.TUT)) {
                    Pattern pattern = Pattern.compile(RegexStrings.TUT);
                    Matcher matcher = pattern.matcher(line);
                    matcher.matches();

                    //make new lab and add to lab set
                    Lab newLab = new Lab(matcher.group(3), Integer.parseInt(matcher.group(4)), matcher.group(5), Integer.parseInt(matcher.group(6)));
                    ParsedData.LABS.add(newLab);
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in labs(no space)");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    private static String readNotCompatible(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Unwanted:";
        try {
            while (!(line = br.readLine()).trim().matches(exitString)) {
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.NOTCOMPATABLE_FORMAT)) {
                    System.out.println("Parsing error: Could not parse File in Not Compatible");
                    System.out.println(line);
                }
                if (line.matches(RegexStrings.NOTCOMPATABLE_FORMAT)) {
                    Matcher matcher = Pattern.compile(RegexStrings.NOTCOMPATABLE_FORMAT).matcher(line);
                    matcher.matches();
                    String[] splitUnwanted = line.split(",");
                    Course itemOne = checkPairs(splitUnwanted[0], matcher);
                    Course itemTwo = checkPairs(splitUnwanted[1], matcher);
                    }
                lastLine = line;

                if (!lastLine.matches("[\\s]*")) {
                    System.out.println("Parsing error: Could not parse File in Not Compatible (no space)");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    //TODO waiting on hannah Object Updates
    private static Course checkPairs(String notCompItem, Matcher matcher) {
        Course item = null;
        if (notCompItem.matches(".*(TUT|LAB).*")) {
            if (notCompItem.matches(RegexStrings.LABS)) {
                //make new course slot and add to courseSlot hashset
                //make sure that this
                item = new Lab(matcher.group(3), Integer.parseInt(matcher.group(4)), matcher.group(5), Integer.parseInt(matcher.group(6)));
            } else if (notCompItem.matches(RegexStrings.TUT)) {
                //TODO fill in tutorial constructor when hannah is done
               // item = new Lab();
            }
            //TODO fill in course constructor when hannah is done
        } else{} //item = new Course();
        return item;
    }

    private static String readUnwanted(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Preferences:";
        try {
            while (!(line = br.readLine()).trim().matches(exitString)) {
                //System.out.println(line);
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.UNWANTED)) {
                    System.out.println("Parsing error: Could not parse File in UnWanted");
                    System.out.println(line);
                }
                if (line.matches(RegexStrings.UNWANTED)) {
                    Matcher matcher = Pattern.compile(RegexStrings.UNWANTED).matcher(line);
                    matcher.matches();
                    if (line.matches(".*(TUT|LAB).*")) {
                        if (line.matches(RegexStrings.UNWANTED_L)){

                        }
                        else if (line.matches(RegexStrings.UNWANTED_T)){

                        }
                    } else System.out.println(line + " COURSE");
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Unwanted(no space)");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private static String readPreferences(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Pair:";
        try {
            while (!(line = br.readLine()).trim().matches(exitString)) {
                //System.out.println(line);
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.PREFERENCES)) {
                    System.out.println("Parsing error: Could not parse File in Preferences");
                    System.out.println(line);
                }
                if (line.matches(RegexStrings.PREFERENCES)) {
                    Matcher matcher = Pattern.compile(RegexStrings.PREFERENCES).matcher(line);
                    matcher.matches();
                    if (line.matches(".*(TUT|LAB).*")) {
                        if (line.matches(RegexStrings.PREFERENCES_L)) {
                            //TODO create lab object and possibly a time object
                        }
                        else if (line.matches(RegexStrings.PREFERENCES_T)) {

                        }
                    } else if (line.matches(RegexStrings.PREFERENCES_C)) {

                    }
                }
                lastLine = line;
            }
            //System.out.println(IO.RegexStrings.PREFERENCES);
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Preferences(no space)");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private static String readPair(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Partial assignments:";
        try {
            while (!(line = br.readLine()).trim().matches(exitString)) {
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.PAIR)) {
                    System.out.println("Parsing error: Could not parse File in Pair");
                    System.out.println(line);
                }
                if (line.matches(RegexStrings.PAIR)) {
                    Matcher matcher = Pattern.compile(RegexStrings.PAIR).matcher(line);
                    matcher.matches();
                    String[] splitPair = line.split(",");
                    Course item1 = checkPairs(splitPair[0], matcher);
                    Course item2 = checkPairs(splitPair[1], matcher);
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Pair(no space)");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private static void readPartialAssignment(BufferedReader br, String line) {
        String lastLine = line;
        try {
            while (!((line = br.readLine()) == null)) {
                if (line.matches("[\\s]*")) {
                    lastLine = line;
                    continue;
                }
                if (!line.matches(RegexStrings.UNWANTED)) {
                    System.out.println("Parsing error: Could not parse File in Partial Assignment ");
                    System.out.println(line);
                }
                if (line.matches(RegexStrings.UNWANTED)) {
                    Matcher matcher = Pattern.compile(RegexStrings.UNWANTED).matcher(line);
                    if (line.matches(".*(TUT|LAB).*")) {
                        if (line.matches(RegexStrings.UNWANTED_L)) System.out.println(line + " LAB");
                        else if (line.matches(RegexStrings.UNWANTED_T)) System.out.println(line + " TUT");
                    } else System.out.println(line + " COURSE");
                }
                lastLine = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //String fileName = args[0];
        //Scanner in = new Scanner(System.in);String fileName = in.nextLine();
        for (String x : args) {

            inputReader(x);
            System.out.println("_______________________________");
        }
    }
}