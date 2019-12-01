package IO;

import DataClass.*;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 */
public class Parser {

    private static Matcher matcher;

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
                   // System.out.println(zonesRead.toString());
                    line = readNotCompatible(br, line);
                }
                if (line.trim().matches("Unwanted:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 6) {
                    zonesRead.add(line.trim());
                    //System.out.println(zonesRead.toString());
                    line = readUnwanted(br, line);

                }
                if (line.trim().matches("Preferences:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 7) {
                    zonesRead.add(line.trim());
                   // System.out.println(zonesRead.toString());
                    line = readPreferences(br, line);
                }
                if (line.trim().matches("Pair:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 8) {
                    zonesRead.add(line.trim());
                    //System.out.println(zonesRead.toString());
                    line = readPair(br, line);
                }
                if (line.trim().matches("Partial assignments:") && !zonesRead.contains(line.trim()) && zonesRead.size() == 9) {
                    zonesRead.add(line.trim());
                   // System.out.println(zonesRead.toString());
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
    //TODO need to check for duplicates

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
                    //System.exit(0);
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
                //System.exit(0);
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
                //System.exit(0);
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
                    //System.exit(0);
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
               // System.exit(0);
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
                    //TODO check for dups
                    Lab newLab = new Lab(labName, labNumber, matcher.group(6), Integer.parseInt(matcher.group(7)), Integer.parseInt(matcher.group(5)));
                    ParsedData.LABS.add(newLab);
                }
                if (line.matches(RegexStrings.TUT)) {
                    Pattern pattern = Pattern.compile(RegexStrings.TUT);
                    Matcher matcher = pattern.matcher(line);
                    matcher.matches();
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
                    String[] splitUnwanted = line.split(",");

                    Course itemOne = getPairs(splitUnwanted[0]);
                    Course itemTwo = getPairs(splitUnwanted[1]);

                    NotCompatibleCoursePair notCompatibleCoursePair = new NotCompatibleCoursePair(itemOne, itemTwo);
                    ParsedData.NOT_COMPATIBLE.add(notCompatibleCoursePair);
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Not Compatible (no space)");
                System.exit(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    private static Course getPairs(String notCompItem) {
        Course item = null;
        if (notCompItem.matches(".*(TUT|LAB).*")) {
            if (notCompItem.matches(RegexStrings.LABS)) {
                Matcher matcher = Pattern.compile(RegexStrings.LABS).matcher(notCompItem);
                matcher.matches();

                item = new Lab(matcher.group(2), Integer.parseInt(matcher.group(3)), matcher.group(6), Integer.parseInt(matcher.group(7)), Integer.parseInt(matcher.group(5)));
            } else if (notCompItem.matches(RegexStrings.TUT)) {
                Matcher matcher = Pattern.compile(RegexStrings.TUT).matcher(notCompItem);
                matcher.matches();

                item = new Lab(matcher.group(3), Integer.parseInt(matcher.group(4)), matcher.group(5), Integer.parseInt(matcher.group(6)));
            }
        } else {
            Matcher matcher = Pattern.compile(RegexStrings.COURSES).matcher(notCompItem);
            matcher.matches();
            item = new Section(matcher.group(2), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(5)));
        }
        return item;
    }

    private static String readUnwanted(BufferedReader br, String line) {
        Course course;
        Slot slot;
        float minToDecimal;
        UnwantedCourseTime unwantedCourse;
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
                    if (line.matches(".*(TUT|LAB).*")) {

                        if (line.matches(RegexStrings.LAB_DAY_TIME)) {
                            matcher = Pattern.compile(RegexStrings.LAB_DAY_TIME).matcher(line);
                            matcher.matches();
                            minToDecimal = Float.parseFloat(matcher.group(12)) + (Float.parseFloat(matcher.group(16)) / 60);
                            course = new Lab(matcher.group(2), Integer.parseInt(matcher.group(3)), matcher.group(6), Integer.parseInt(matcher.group(7)), Integer.parseInt(matcher.group(5)));

                            slot = checkSlot("lab", matcher.group(8), minToDecimal);
                            ParsedData.UNWANTED.add(unwantedCourse = new UnwantedCourseTime(course, slot));
                        } else if (line.matches(RegexStrings.TUT_DAY_TIME)) {
                            matcher = Pattern.compile(RegexStrings.TUT_DAY_TIME).matcher(line);
                            matcher.matches();

                            minToDecimal = Float.parseFloat(matcher.group(11)) + (Float.parseFloat(matcher.group(14)) / 60);
                            course = new Lab(matcher.group(3), Integer.parseInt(matcher.group(4)), matcher.group(5), Integer.parseInt(matcher.group(6)));

                            slot = checkSlot("lab", matcher.group(7), minToDecimal);
                            ParsedData.UNWANTED.add(new UnwantedCourseTime(course, slot));
                        }
                    } else {
                        matcher = Pattern.compile(RegexStrings.CRS_DAY_TIME).matcher(line);
                        matcher.matches();
                        if (!matcher.group(6).matches(RegexStrings.COURSE_VALID_DAY)) {
                            System.out.println("Invalid Day");
                            continue;
                        }

                        minToDecimal = Float.parseFloat(matcher.group(10)) + (Float.parseFloat(matcher.group(14)) / 60);
                        course = new Section(matcher.group(2), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(5)));

                        slot = checkSlot("course", matcher.group(6), minToDecimal);
                        ParsedData.UNWANTED.add(new UnwantedCourseTime(course, slot));

                    }
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
        Course course;
        Slot slot;
        PreferredCourseTime prefCourse;
        float minToDecimal;
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

                    if (line.matches(".*(TUT|LAB).*")) {
                        if (line.matches(RegexStrings.PREFERENCES_L)) {

                            matcher = Pattern.compile(RegexStrings.PREFERENCES_L).matcher(line);
                            matcher.matches();


                            minToDecimal = Float.parseFloat(matcher.group(6)) + (Float.parseFloat(matcher.group(10)) / 60);

                            course = new Lab(matcher.group(12), Integer.parseInt(matcher.group(13)), matcher.group(16), Integer.parseInt(matcher.group(17)), Integer.parseInt(matcher.group(15)));

                            slot = checkSlot("lab", matcher.group(1), minToDecimal);
                            if (slot == null) {
                                System.out.println("Invalid Time");
                                continue;
                            }
                            ParsedData.PREFERENCES.add(new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(19))));
                        } else if (line.matches(RegexStrings.PREFERENCES_T)) {
                            matcher = Pattern.compile(RegexStrings.PREFERENCES_T).matcher(line);
                            matcher.matches();

                            minToDecimal = Float.parseFloat(matcher.group(5)) + (Float.parseFloat(matcher.group(9)) / 60);
                            course = new Lab(matcher.group(12), Integer.parseInt(matcher.group(13)), matcher.group(14), Integer.parseInt(matcher.group(15)));

                            slot = checkSlot("lab", matcher.group(1), minToDecimal);
                            if (slot == null) {
                                System.out.println("Invalid Time");
                                continue;
                            }
                            ParsedData.PREFERENCES.add(new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(15))));
                        }
                    } else {
                        matcher = Pattern.compile(RegexStrings.PREFERENCES_C).matcher(line);
                        matcher.matches();

                        minToDecimal = Float.parseFloat(matcher.group(5)) + (Float.parseFloat(matcher.group(9)) / 60);
                        course = new Section(matcher.group(11), Integer.parseInt(matcher.group(12)), Integer.parseInt(matcher.group(14)));
                        slot = checkSlot("course", matcher.group(1), minToDecimal);
                        if (slot == null) {
                            System.out.println("Invalid Time");
                            continue;
                        }

                        ParsedData.PREFERENCES.add(new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(15).trim())));
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
    private static void count(){
        int groupCount = matcher.groupCount();

        for (int i = 0; i <= groupCount; i++) {
            // Group i substring
            System.out.println("Group " + i + ": " + matcher.group(i));
        }
    }
    private static Slot checkSlot(String slotLabCourse, String day, float time) throws NullPointerException {
        LinkedHashSet<Slot> listToParse = null;
        switch (slotLabCourse) {
            case "course":
                listToParse = new LinkedHashSet<>(ParsedData.COURSE_SLOTS);

                break;
            case "lab":
                listToParse = new LinkedHashSet<>(ParsedData.LAB_SLOT);

                break;
            default:
                System.out.println("no match");
        }

        for (Slot s : listToParse) {
            //System.out.println(s.getDay().toString()+s.getStartTime()+","+day+time);
            if (s.getDay().equals(Slot.Day.valueOf(day.trim())) && s.getStartTime() == time) {
                return s;
            }
        }
        return null;
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
                    Course itemOne = getPairs(splitPair[0]);
                    Course itemTwo = getPairs(splitPair[1]);

                    PreferredCoursePair coursePair = new PreferredCoursePair(itemOne, itemTwo);
                    ParsedData.PAIR.add(coursePair);
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Pair(no space)");
                //System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private static void readPartialAssignment(BufferedReader br, String line) {
        String lastLine = line;
        float minToDecimal;
        Course course;
        Slot slot;
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

                    if (line.matches(".*(TUT|LAB).*")) {
                        if (line.matches(RegexStrings.LAB_DAY_TIME)) {
                            matcher = Pattern.compile(RegexStrings.LAB_DAY_TIME).matcher(line);
                            matcher.matches();

                            minToDecimal = Float.parseFloat(matcher.group(12)) + (Float.parseFloat(matcher.group(16)) / 60);
                            course = new Lab(matcher.group(2), Integer.parseInt(matcher.group(3)), matcher.group(6), Integer.parseInt(matcher.group(7)), Integer.parseInt(matcher.group(5)));

                            slot = checkSlot("lab", matcher.group(8), minToDecimal);
                            if (slot == null) {
                                System.out.println("Invalid Time");
                                continue;
                            }
                            ParsedData.PARTIAL_ASSIGNMENTS.add(new PreAssignedCourseTime(course, slot));
                        } else if (line.matches(RegexStrings.TUT_DAY_TIME)) {
                            matcher = Pattern.compile(RegexStrings.TUT_DAY_TIME).matcher(line);
                            matcher.matches();

                            minToDecimal = Float.parseFloat(matcher.group(11)) + (Float.parseFloat(matcher.group(14)) / 60);
                            course = new Lab(matcher.group(3), Integer.parseInt(matcher.group(4)), matcher.group(5), Integer.parseInt(matcher.group(6)));

                            slot = checkSlot("lab", matcher.group(7), minToDecimal);
                            if (slot == null) {
                                System.out.println("Invalid Time");
                                continue;
                            }
                            ParsedData.PARTIAL_ASSIGNMENTS.add(new PreAssignedCourseTime(course, slot));
                        }
                    } else {
                        matcher = Pattern.compile(RegexStrings.CRS_DAY_TIME).matcher(line);
                        matcher.matches();
                        if (!matcher.group(6).matches(RegexStrings.COURSE_VALID_DAY)) {
                            System.out.println("Invalid Day");
                            continue;
                        }
                        minToDecimal = Float.parseFloat(matcher.group(10)) + (Float.parseFloat(matcher.group(14)) / 60);
                        course = new Section(matcher.group(2), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(5)));

                        slot = checkSlot("course", matcher.group(6), minToDecimal);
                        if (slot == null) {
                            System.out.println("Invalid Time");
                            continue;
                        }
                        ParsedData.PARTIAL_ASSIGNMENTS.add(new PreAssignedCourseTime(course, slot));
                    }
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
//        for (String x : args) {
//
//            inputReader(x);
//            System.out.println("_______________________________");
//        }
        inputReader("poop.txt");
    }
}
