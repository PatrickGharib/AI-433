package IO;

import DataClass.*;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static Matcher matcher;

    public static void inputReader(String fileName) {
        String[] zones = {"Name:","Course slots:","Lab slots:", "Courses:","Labs:","Not compatible:","Unwanted:","Preferences:","Pair:","Partial assignments:"};
        List<String> zonesRead = new ArrayList<String>();
        String zone;
        FileReader fr = null;
        BufferedReader br = null;
        String line = "";
        System.out.println(fileName);
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            int count =0;
            while ((zone = br.readLine()) != null){
                if(count < 10){
                    if (zone.trim().equals(zones[count]) && !zonesRead.contains(zone.trim()) && zonesRead.isEmpty()){
                        zonesRead.add(zone.trim());
                        count++;
                    }
                    if (zone.trim().equals(zones[count]) && !zonesRead.contains(zone.trim()) && zonesRead.size() == count){
                        zonesRead.add(zone.trim());
                        count++;
                    }
                }
            }
            if (count!= 10 ){
                System.out.println("Could not Parse input");
                System.exit(0);
            }
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                if (line.trim().matches("Name:")) {
                    line = readName(br, line);
                }
                if (line.trim().matches("Course slots:")) {
                    line = readCourseSlot(br, line);
                }
                if (line.trim().matches("Lab slots:")) {
                    line = readLabSlot(br, line);
                }
                if (line.trim().matches("Courses:")) {
                    line = readCourse(br, line);
                }
                if (line.trim().matches("Labs:")) {
                    line = readLab(br, line);
                }
                if (line.trim().matches("Not compatible:")) {
                    line = readNotCompatible(br, line);
                }
                if (line.trim().matches("Unwanted:")) {
                    line = readUnwanted(br, line);
                }
                if (line.trim().matches("Preferences:")) {
                    line = readPreferences(br, line);
                }
                if (line.trim().matches("Pair:") ) {
                    line = readPair(br, line);
                }
                if (line.trim().matches("Partial assignments:")) {
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
            System.out.println("Could not parse input");
        }
    }


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
                // System.Exit(0)
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
                    if (!slotWasMade) continue;
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
                    System.out.println("Parsing message: Could not parse line" + line + " in Lab slot");
                }
                if (line.matches(RegexStrings.LAB_SLOTS)) {
                    boolean slotWasMade = setSlot(line, RegexStrings.LAB_SLOTS, 1);

                    if (!slotWasMade) continue;
                    lastLine = line;
                }
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing message: Could not parse line:" + line + " in Lab slot(no space)");
                //System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    private static boolean setSlot(String line, String regex, int slotToMake) {
        float minuteConversion = 60f;
        Matcher matcher = Pattern.compile(regex).matcher(line);
        matcher.matches();
        //convert minutes to float(minutes/60)
        float minToDecimal = Float.parseFloat(matcher.group(3)) + (Float.parseFloat(matcher.group(4)) / minuteConversion);
        String slotDay = matcher.group(2);

        try {
            if (slotToMake == 0) {
                CourseSlot newCourseSlot = new CourseSlot(slotDay, minToDecimal, Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
                addData(ParsedData.COURSE_SLOTS, newCourseSlot);
            } else {
                LabSlot newLabSlot = new LabSlot(slotDay, minToDecimal, Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
                addData(ParsedData.LAB_SLOT, newLabSlot);
            }
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
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
                    if (newCourse.getCourseName().equals("CPSC") && newCourse.getCourseNumber() == 313 )
                        addData(ParsedData.LABS, new Lab("CPSC", 813, "LEC", 1));
                    if (newCourse.getCourseName().equals("CPSC") && newCourse.getCourseNumber() == 413 )
                        addData(ParsedData.LABS, new Lab("CPSC", 913, "LEC", 1));

                    addData(ParsedData.COURSES, newCourse);
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
                }
                Matcher matcher = Pattern.compile(RegexStrings.TUT_LABS).matcher(line);
                matcher.matches();
                if (line.matches(RegexStrings.LABS)) {

                    Lab newLab = new Lab(matcher.group(7), Integer.parseInt(matcher.group(8)), matcher.group(11), Integer.parseInt(matcher.group(12)), Integer.parseInt(matcher.group(10)));
                    addData(ParsedData.LABS, newLab);
                }
                if (line.matches(RegexStrings.TUT)) {
                    Lab newLab = new Lab(matcher.group(2), Integer.parseInt(matcher.group(3)), matcher.group(4), Integer.parseInt(matcher.group(5)));
                    addData(ParsedData.LABS, newLab);
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in labs(no space)");
                // System.exit(0);
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
                    Course itemOne = generateCourseObject(splitUnwanted[0]);
                    Course itemTwo = generateCourseObject(splitUnwanted[1]);
                    NotCompatibleCoursePair notCompatibleCoursePair = new NotCompatibleCoursePair(itemOne, itemTwo);
                    addData(ParsedData.NOT_COMPATIBLE, notCompatibleCoursePair);

                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Not Compatible (no space)");
                // System.exit(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    private static Course generateCourseObject(String courseItem) {
        Course item = null;
        Matcher matcher = Pattern.compile(RegexStrings.PAIRS_GROUPS).matcher(courseItem);
        matcher.matches();
        if (courseItem.matches(".*(TUT|LAB).*")) {
            if (courseItem.matches(RegexStrings.LABS)) {
                item = new Lab(matcher.group(2), Integer.parseInt(matcher.group(3)), matcher.group(6), Integer.parseInt(matcher.group(7)), Integer.parseInt(matcher.group(5)));
            } else if (courseItem.matches(RegexStrings.TUT)) {

                item = new Lab(matcher.group(9), Integer.parseInt(matcher.group(10)), matcher.group(11), Integer.parseInt(matcher.group(12)));
            }
        } else {

            item = new Section(matcher.group(14), Integer.parseInt(matcher.group(15)), Integer.parseInt(matcher.group(17)));
        }
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
                    boolean makeSuccessful = setIdenDayTime(line, 0);
                    if (!makeSuccessful) continue;
                }
                lastLine = line;
            }
            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Unwanted(no space)");
                // System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private static boolean setIdenDayTime(String line, int objectToMake) {
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

                if (slot == null) {
                    System.out.println("Invalid Time, no slot at: " + matcher.group(7) + "," + matcher.group(8) + ":" + matcher.group(9) + "\nline: " + line + "\n");
                    return false;
                }
            } else if (line.matches(RegexStrings.TUT_DAY_TIME)) {

                matcher = Pattern.compile(RegexStrings.TUT_DAY_TIME).matcher(line);
                matcher.matches();
                minToDecimal = Float.parseFloat(matcher.group(6)) + (Float.parseFloat(matcher.group(7)) / 60);
                course = new Lab(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3), Integer.parseInt(matcher.group(4)));
                slot = checkSlot("lab", matcher.group(5), minToDecimal);
                if (slot == null) {
                    System.out.println("Invalid Time, no slot at: " + matcher.group(5) + "," + matcher.group(6) + ":" + matcher.group(7) + "\nline: " + line + "\n");
                    return false;
                }
            }
        } else {
            matcher = Pattern.compile(RegexStrings.CRS_DAY_TIME).matcher(line);
            matcher.matches();

            if (!matcher.group(5).matches(RegexStrings.COURSE_VALID_DAY)) {
                System.out.println("Invalid Day: " + line);
                return false;
            }

            minToDecimal = Float.parseFloat(matcher.group(6)) + (Float.parseFloat(matcher.group(7)) / 60);
            course = new Section(matcher.group(1), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(4)));
            slot = checkSlot("course", matcher.group(5), minToDecimal);
            if (slot == null) {
                System.out.println("Invalid Time, no slot at: " + matcher.group(5) + "," + matcher.group(6) + ":" + matcher.group(7) + "\nline: " + line + "\n");
                return false;
            }
        }

        if (objectToMake == 0) addData(ParsedData.UNWANTED, new UnwantedCourseTime(course, slot));
        else addData(ParsedData.PARTIAL_ASSIGNMENTS, new PreAssignedCourseTime(course, slot));

        return true;
    }

    private static String readPreferences(BufferedReader br, String line) {
        Course course;
        Slot slot;
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
                                System.out.println("Invalid Time, no slot at: " + matcher.group(2) + matcher.group(3) + "\nline: " + line + "\n");
                                continue;
                            }
                            addData(ParsedData.PREFERENCES, new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(13))));
                        } else if (line.matches(RegexStrings.PREFERENCES_T)) {
                            matcher = Pattern.compile(RegexStrings.PREFERENCES_T).matcher(line);
                            matcher.matches();

                            minToDecimal = Float.parseFloat(matcher.group(4)) + (Float.parseFloat(matcher.group(5)) / 60);
                            course = new Lab(matcher.group(7), Integer.parseInt(matcher.group(8)), matcher.group(9), Integer.parseInt(matcher.group(10)));

                            slot = checkSlot("lab", matcher.group(2), minToDecimal);
                            if (slot == null) {
                                System.out.println("Invalid Time, no slot at: " + matcher.group(2) + "," + matcher.group(3) + "\nline: " + line + "\n");
                                continue;
                            }
                            addData(ParsedData.PREFERENCES, new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(11))));
                        }
                    } else {
                        matcher = Pattern.compile(RegexStrings.PREFERENCES_C).matcher(line);
                        matcher.matches();

                        minToDecimal = Float.parseFloat(matcher.group(4)) + (Float.parseFloat(matcher.group(5)) / 60);
                        course = new Section(matcher.group(7), Integer.parseInt(matcher.group(8)), Integer.parseInt(matcher.group(10)));
                        slot = checkSlot("course", matcher.group(2), minToDecimal);
                        if (slot == null) {
                            System.out.println("Invalid Time, no slot at: " + matcher.group(2) + matcher.group(3) + "\nline: " + line + "\n");
                            continue;
                        }

                        addData(ParsedData.PREFERENCES, new PreferredCourseTime(slot, course, Integer.parseInt(matcher.group(11))));
                    }
                }
                lastLine = line;
            }

            if (!lastLine.matches("[\\s]*")) {
                System.out.println("Parsing error: Could not parse File in Preferences(no space)");
                // System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private static Slot checkSlot(String slotLabCourse, String day, float time) {
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
            if (s.getDay().equals(Slot.Day.valueOf(day.trim())) && Float.compare(s.getStartTime(), time) == 0) {
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
                    String[] splitPair = line.split(",");
                    Course itemOne = generateCourseObject(splitPair[0]);
                    Course itemTwo = generateCourseObject(splitPair[1]);

                    PreferredCoursePair coursePair = new PreferredCoursePair(itemOne, itemTwo);
                    addData(ParsedData.PAIR, coursePair);
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
                    boolean makeSuccessful = setIdenDayTime(line, 1);
                    if (!makeSuccessful) continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <E> void addData(LinkedHashSet<E> parsedDataHolder, E data)
    {
        if(!parsedDataHolder.add(data)) System.out.println("The " + data.getClass().toString() + " :" + data.toString() + " already exists and will not be added twice.");
    }

    public static void main(String[] args) {
        //String fileName = args[0];
        //Scanner in = new Scanner(System.in);String fileName = in.nextLine();
//        for (String x : args) {
//
//            inputReader(x);
//            System.out.println("_______________________________");
//        }
        inputReader("example1.txt");
        System.out.println("finished");
    }
}
