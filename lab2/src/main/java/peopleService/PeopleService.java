package peopleService;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Controller;
import dao.PeopleDAO;
import peopleClasses.Student;
import peopleClasses.Subjects;
import peopleClasses.Teacher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public abstract class PeopleService {
     private final static String DIR_PATH = "src/main/resources/PersonsFiles/";
     private final static PeopleDAO dao = new PeopleDAO(DIR_PATH);


     public static void createTeacher(Map attributes) throws IOException {
          Teacher teacher = new Teacher(nextId(),
                  (String) attributes.get("fullname"),
                  (int) attributes.get("birthYear"),
                  (String) attributes.get("phoneNumber"),
                  (Subjects) attributes.get("subject"),
                  (String) attributes.get("workingHours"));
          dao.save(teacher, "T");
     }
     public static void createStudent(Map attributs) throws IOException {
          Student student = new Student(nextId(),
                  (String) attributs.get("fullname"),
                  (int) attributs.get("birthYear"),
                  (String) attributs.get("phoneNumber"),
                  (Map) attributs.get("averageMarks"),
                  (Subjects[]) attributs.get("subjects"));
          dao.save(student, "S");
     }

     public static void edit(Map attributes) throws IOException {
          int id = nextId();
          String type = dao.determineClass(id);
          if (type == null) {
               Controller.returnError("Person with this id does not exist");
               return;
          }
          if (type.equals("T")) {
               Teacher teacher = dao.get(id, type);
               if (attributes.containsKey("fullname")) teacher.setFullName((String) attributes.get("fullname"));
               if (attributes.containsKey("birthYear")) teacher.setBirthYear((int) attributes.get("birthYear"));
               if (attributes.containsKey("phoneNumber")) teacher.setPhoneNumber((String) attributes.get("phoneNumber"));
               if (attributes.containsKey("subject")) teacher.setSubject((Subjects) attributes.get("subject"));
               if (attributes.containsKey("workingHours")) teacher.setWorkingHours((String) attributes.get("workingHours"));
               dao.update(teacher, "T");
          } else {
               Student student = dao.get(id, type);
               if (attributes.containsKey("fullname")) student.setFullName((String) attributes.get("fullname"));
               if (attributes.containsKey("birthYear")) student.setBirthYear((int) attributes.get("birthYear"));
               if (attributes.containsKey("phoneNumber")) student.setPhoneNumber((String) attributes.get("phoneNumber"));
               if (attributes.containsKey("averageMarks")) student.setAverageMarks((Map) attributes.get("averageMarks"));
               if (attributes.containsKey("subjects")) student.setSubjects((Subjects[]) attributes.get("subjects"));
               dao.update(student, "S");
          }
     }

     public static void delete(int id) throws IOException {
          String type = dao.determineClass(id);
          if (type == null) Controller.returnError("Person with this id does not exist");
          dao.delete(id, type);
     }



     private static int nextId() throws IOException {
          File dir = new File(DIR_PATH);
          File[] persons = dir.listFiles();
          if (persons.length == 0) return 0;
          else {
               String currentId;
               for (int i = 0; i < persons.length; i++) {
                    currentId = persons[i].getName();
                    if (Integer.valueOf(currentId.substring(0, currentId.length() - 6)) != i)
                         return i;
               }
          }
          return persons.length;
     }
}
