import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Parser {

    private static List<String> zonesRead;
    public static void inputReader(String fileName) {
        String zone;
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
                zone = line.trim();
                switch(zone){
                    case "Name:":
                        if (zonesRead.size() != 0)
                            System.out.println("parsng error");
                            System.exit(0);
                        zonesRead.add(zone);
                        readName(br);
                        break;
                    case "Course slots:":

                        break;
                    case "Lab slots:":
                        break;
                    case "Courses:":
                        break;
                    case "Labs:":
                        break;
                    case "Not compatible:":
                        break;
                    case "Unwanted:":
                        break;
                    case "Prefences:":
                        break;
                    case "Pair:":
                        break;
                    case "Partial assignment:":
                        break;
                }

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

    private static void readName(BufferedReader br) {

        while (true){
            try {

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main (String[]args){
        String fileName = args[0];
        inputReader(fileName);
    }
}