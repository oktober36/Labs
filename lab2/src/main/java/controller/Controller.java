package controller;

import peopleClasses.Subjects;
import queue.Queue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public abstract class Controller {
    private final static File DIR = new File("src/main/resources/commands/");

    private final static String ERROR_PATH = "src/main/resources/errors/";

    private static String error = null;

    private static int countErrors =  new File(ERROR_PATH).listFiles().length;

    public static Queue<Map<String, Object>> queue = new Queue<Map<String, Object>>();

    private static final String PATTERN1 = "create teacher ([A-Z][a-z]* ){2}[A-Z][a-z]* " +
            "(19|20)\\d{2} \\d{11} (physics|math|coding) \\d{2}:\\d{2}-\\d{2}:\\d{2}( )*";

    private static final String PATTERN2 = "create student ([A-Z][a-z]* ){2}[A-Z][a-z]* (19|20)\\d{2} " +
            "\\d{11} (physics|math|coding) \\d\\.\\d{2}( (physics|math|coding) \\d(\\.)\\d{2}){0,2}( )*";

    private static final String PATTERN3 = "edit teacher \\d{1,4}( ([A-Z][a-z]* ){2}[A-Z][a-z]*)?" +
            "( (19|20)\\d{2})?( \\d{11})?( (physics|math|coding))?( \\d{2}:\\d{2}-\\d{2}:\\d{2})?( )*";

    public static final String PATTERN4 = "edit student \\d{1,4}( ([A-Z][a-z]* ){2}[A-Z][a-z]*)?( (19|20)\\d{2})?" +
            "( \\d{11})?( (physics|math|coding) \\d\\.\\d{2}( (physics|math|coding) \\d\\.\\d{2}){0,2})?( )*";

    private static final String PATTERN5 = "delete \\d{1,4}( )*";


    public static void start() {
       new Thread()  {
            public void run() {
                while (true) {
                    try {
                        readFiles();
                        TimeUnit.SECONDS.sleep(1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }.start();
    }


    public static void readFiles() throws IOException, InterruptedException {
        File[] files = DIR.listFiles();

        for (int i = 0; i < files.length; i++) {
            Scanner scanner = new Scanner(files[i]);
            Map<String, Object> attributes = new HashMap<String, Object>();
            StringBuilder stringBuilder = new StringBuilder();

            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append(" ");
            }
            scanner.close();
            String lines = stringBuilder.toString();
            String[] splitedLines = lines.split(" ");

            if (Pattern.matches(PATTERN1, lines) || Pattern.matches(PATTERN2, lines)) {
                attributes.put("action", splitedLines[0]);
                attributes.put("type", splitedLines[1]);
                attributes.put("fullname", splitedLines[2] + " " + splitedLines[3] + " " + splitedLines[4]);
                attributes.put("birthYear", Integer.valueOf(splitedLines[5]));
                attributes.put("phoneNumber", splitedLines[6]);
            }

            if (Pattern.matches(PATTERN1, lines)) {
                attributes.put("subject", Subjects.valueOf(splitedLines[7]));
                attributes.put("workingHours", splitedLines[8]);

            }
            if (Pattern.matches(PATTERN2, lines)) {
                Map averageMarks = new HashMap();
                Subjects[] subjects = new Subjects[(splitedLines.length - 7)/2];
                for (int j = 7; j < splitedLines.length; j+=2) {
                    averageMarks.put(Subjects.valueOf(splitedLines[j]), Float.valueOf(splitedLines[j+1]));
                    subjects[(j-7)/2] = Subjects.valueOf(splitedLines[j]);
                }
                attributes.put("subjects", subjects);
                attributes.put("averageMarks", averageMarks);
            }
            if (Pattern.matches(PATTERN3, lines) || Pattern.matches(PATTERN4, lines)) {
                attributes.put("action", splitedLines[0]);
                attributes.put("type", splitedLines[1]);
                attributes.put("id", Integer.valueOf(splitedLines[2]));
                int j = 3;
                if (j < splitedLines.length && Pattern.matches("([A-Z][a-z]* ){2}[A-Z][a-z]*", splitedLines[3]))
                {
                    attributes.put("fullname", splitedLines[3] + " " + splitedLines[4] + " " + splitedLines[5]);
                    j++;
                }
                if (j < splitedLines.length && Pattern.matches("(19|20)\\d{2}", splitedLines[j])) {
                    attributes.put("birthYear", Integer.valueOf(splitedLines[j]));
                    j++;
                }
                if (j < splitedLines.length && Pattern.matches("\\d{11}", splitedLines[j]) && j < splitedLines.length) {
                    attributes.put("phoneNumber", splitedLines[j]);
                    j++;
                }
                if (Pattern.matches(PATTERN3, lines)) {
                    if (j < splitedLines.length && Pattern.matches("(physics|math|coding)", splitedLines[j])) {
                        attributes.put("subject", Subjects.valueOf(splitedLines[j]));
                        j++;
                    }
                    if (j < splitedLines.length && Pattern.matches("\\d{2}:\\d{2}-\\d{2}:\\d{2}", splitedLines[j])) {
                        attributes.put("workingHours", splitedLines[j]);
                    }
                } else {
                    if (j < splitedLines.length && Pattern.matches("(physics|math|coding)", splitedLines[j])) {
                        Map averageMarks = new HashMap();
                        Subjects[] subjects = new Subjects[(splitedLines.length - j)/2];
                        for (int k = j; k < splitedLines.length; k+=2) {
                            averageMarks.put(Subjects.valueOf(splitedLines[k]), Float.valueOf(splitedLines[k+1]));
                            subjects[(k-j)/2] = Subjects.valueOf(splitedLines[k]);
                        }
                        attributes.put("subjects", subjects);
                        attributes.put("averageMarks", averageMarks);
                    }
                }
            }

            if (Pattern.matches(PATTERN5, lines)){
                attributes.put("action", splitedLines[0]);
                attributes.put("id", Integer.valueOf(splitedLines[1]));
            }
            if (!Pattern.matches(PATTERN1, lines) && !Pattern.matches(PATTERN2, lines) && !Pattern.matches(PATTERN3, lines)
            && !Pattern.matches(PATTERN4, lines) && !Pattern.matches(PATTERN5, lines)) {
                returnError("Invalid command");
            }
            TimeUnit.SECONDS.sleep(1);

            if (!(error == null)) {
                File errorFile = new File(ERROR_PATH + error);
                Files.copy(files[i].toPath(), Path.of(ERROR_PATH + countErrors + error + ".txt"));
                error = null;
            }

            files[i].delete();

            if (attributes.size() > 0)  queue.offer(attributes);

        }
    }

    public static void returnError(String error1) {
        error = error1;
        countErrors++;
    }
}