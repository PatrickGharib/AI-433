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
    private static String readCourseSlot(BufferedReader br, String line) {
        String lastLine = line;
        String exitString = "Lab slots:";
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
                    boolean slotWasMade = setSlot(line, RegexStrings.COURSE_SLOTS, 0);
                    if (!slotWasMade)continue;
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
                    System.out.println("Parsing message: Could not parse line"+ line +" in Lab slot");
                }
                if (line.matches(RegexStrings.LAB_SLOTS)) {
                    boolean slotWasMade = setSlot(line, RegexStrings.COURSE_SLOTS, 1);

                    if (!slotWasMade)continue;
                    lastLine = line;
                }
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing message: Could not parse line:" + line + " in Lab slot(no space)");
                //System.exit(0);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
    private static boolean setSlot(String line, String regex, int slotToMake){
        float minuteConversion = 60f;
        Matcher matcher = Pattern.compile(regex).matcher(line);
        matcher.matches();
        //convert minutes to float(minutes/60)
        float minToDecimal = Float.parseFloat(matcher.group(3)) + (Float.parseFloat(matcher.group(4)) / minuteConversion);
        String slotDay = matcher.group(2);

        try {
            if (slotToMake == 0) {
                CourseSlot newCourseSlot = new CourseSlot(slotDay, minToDecimal, Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
                ParsedData.COURSE_SLOTS.add(newCourseSlot);
            }
            else{
                LabSlot newLabSlot = new LabSlot(slotDay, minToDecimal, Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
                ParsedData.LAB_SLOT.add(newLabSlot);
            }
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

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
                    System.out.println("Parsing message: Could not parse line:" + line + " in Courses");
                    System.out.println(line);
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
                System.out.println("Parsing message: Could not parse line:" + line + "  in Courses(no space)");
                // System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
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
                    System.out.println("Parsing message: Could not parse line:" + line + " in labs");
                    System.out.println(line);
                    System.exit(0);
                }
                Matcher matcher = Pattern.compile(RegexStrings.TUT_LABS).matcher(line);
                matcher.matches();
                if (line.matches(RegexStrings.LABS)) {
                    //TODO check for dups
                    Lab newLab = new Lab(matcher.group(7),Integer.parseInt(matcher.group(8)), matcher.group(11), Integer.parseInt(matcher.group(12)), Integer.parseInt(matcher.group(10)));
                    ParsedData.LABS.add(newLab);
                }
                if (line.matches(RegexStrings.TUT)) {
                    Lab newLab = new Lab(matcher.group(2), Integer.parseInt(matcher.group(3)), matcher.group(4), Integer.parseInt(matcher.group(5)));
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
        Matcher matcher = Pattern.compile(RegexStrings.PAIRS_GROUPS).matcher(notCompItem);
        matcher.matches();
        if (notCompItem.matches(".*(TUT|LAB).*")) {
            if (notCompItem.matches(RegexStrings.LABS)) {
                item = new Lab(matcher.group(2), Integer.parseInt(matcher.group(3)), matcher.group(6), Integer.parseInt(matcher.group(7)), Integer.parseInt(matcher.group(5)));
            } else if (notCompItem.matches(RegexStrings.TUT)) {

                item = new Lab(matcher.group(9), Integer.parseInt(matcher.group(10)), matcher.group(11), Integer.parseInt(matcher.group(12)));
            }
        } else {

            item = new Section(matcher.group(14), Integer.parseInt(matcher.group(15)), Integer.parseInt(matcher.group(17)));
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
                    boolean makeSuccessful = setIdenDayTime(line,0);
                    if (!makeSuccessful)continue;
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
    private static boolean setIdenDayTime(String line, int objectToMake){
        Course course = null;
        Slot slot = null;
        float minToDecimal;

        if (line.matches(".*(TUT|LAB).*")) {

            if (line.matches(RegexStrings.LAB_DAY_TIME)) {
                matcher = Pattern.compile(RegexStrings.LAB_DAY_TIME).matcher(line);
                matcher.matches();
                minToDecimal = Float.parseFloat(matcher.group(8)) + (Float.parseFloat(matcher.group(9)) / 60);
                course = new Lab(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(5), Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(4)));
                slot = checkSlot("lab", matcher.group(7), minToDecimal);



            } else if (line.matches(RegexStrings.TUT_DAY_TIME)) {

                matcher = Pattern.compile(RegexStrings.TUT_DAY_TIME).matcher(line);
                matcher.matches();
                minToDecimal = Float.parseFloat(matcher.group(6)) + (Float.parseFloat(matcher.group(7)) / 60);
                course = new Lab(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3), Integer.parseInt(matcher.group(4)));
                slot = checkSlot("lab", matcher.group(5), minToDecimal);
            }
        } else {
            matcher = Pattern.compile(RegexStrings.CRS_DAY_TIME).matcher(line);
            matcher.matches();

            if (!matcher.group(5).matches(RegexStrings.COURSE_VALID_DAY)) {
                System.out.println("Invalid Day: "+ line);
                return false;
            }

            minToDecimal = Float.parseFloat(matcher.group(6)) + (Float.parseFloat(matcher.group(7)) / 60);
            course = new Section(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(4)));
            slot = checkSlot("course", matcher.group(5), minToDecimal);
        }

        try {
            if (objectToMake == 0)ParsedData.UNWANTED.add(new UnwantedCourseTime(course, slot));
            else ParsedData.PARTIAL_ASSIGNMENTS.add(new PreAssignedCourseTime(course, slot));
        } catch (NullPointerException e) {
            System.out.println("Parser message: item  already exists: " + line);
        }

        return true;
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

                            minToDecimal = Float.parseFloat(matcher.group(4)) + (Float.parseFloat(matcher.group(5)) / 60);
                            course = new Lab(matcher.group(7), Integer.parseInt(matcher.group(8)), matcher.group(11), Integer.parseInt(matcher.group(12)), Integer.parseInt(matcher.group(10)));
                            slot = checkSlot("lab", matcher.group(2), minToDecimal);
                            if (slot == null) {
                                System.out.println("Invalid Time, no slot at: "+ matcher.group(2) + matcher.group(3) +"\nline: " + line+ "\n");
                                continue;
                            }
                            ParsedData.PREFERENCES.add(new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(13))));
                        } else if (line.matches(RegexStrings.PREFERENCES_T)) {
                            matcher = Pattern.compile(RegexStrings.PREFERENCES_T).matcher(line);
                            matcher.matches();
                            int groupCount = matcher.groupCount();

                            minToDecimal = Float.parseFloat(matcher.group(4)) + (Float.parseFloat(matcher.group(5)) / 60);
                            course = new Lab(matcher.group(7), Integer.parseInt(matcher.group(8)), matcher.group(9), Integer.parseInt(matcher.group(10)));

                            slot = checkSlot("lab", matcher.group(2), minToDecimal);
                            if (slot == null) {
                                System.out.println("Invalid Time, no slot at: "+ matcher.group(2)+"," + matcher.group(3) +"\nline: " + line+ "\n");
                                continue;
                            }
                            ParsedData.PREFERENCES.add(new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(11))));
                        }
                    } else {
                        matcher = Pattern.compile(RegexStrings.PREFERENCES_C).matcher(line);
                        matcher.matches();

                        minToDecimal = Float.parseFloat(matcher.group(4)) + (Float.parseFloat(matcher.group(5)) / 60);
                        course = new Section(matcher.group(7), Integer.parseInt(matcher.group(8)), Integer.parseInt(matcher.group(10)));
                        slot =  checkSlot("course", matcher.group(2), minToDecimal);
                        if (slot == null) {
                            System.out.println("Invalid Time, no slot at: "+ matcher.group(2) + matcher.group(3) +"\nline: " + line+ "\n");
                            continue;
                        }

                        ParsedData.PREFERENCES.add(new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(11))));
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
        try {
            while (!((line = br.readLine()) == null)) {
                if (line.matches("[\\s]*")) {
                    continue;
                }
                if (!line.matches(RegexStrings.PARTIALASSIGNMENT)) {
                    System.out.println("Parsing error: Could not parse File in Partial Assignment ");
                    System.out.println(line);
                }
                if (line.matches(RegexStrings.PARTIALASSIGNMENT)) {
                    boolean makeSuccessful = setIdenDayTime(line,1);
                    if (!makeSuccessful)continue;
                }
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
        inputReader("new2.txt");
    }
}
