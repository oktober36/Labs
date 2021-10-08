package peopleService;

import DAO.PeopleDAO;
import mainClasses.Student;
import mainClasses.Subjects;
import mainClasses.Teacher;

import java.io.IOException;
import java.util.Map;

public abstract class PeopleService {
     private final static PeopleDAO dao = new PeopleDAO("src/main/resources/PersonsFiles/");

     public static void createTeacher(Map attributes) throws IOException {
          Teacher teacher = new Teacher((int) attributes.get("id"),
                  (String) attributes.get("fullname"),
                  (int) attributes.get("birthYear"),
                  (String) attributes.get("phoneNumber"),
                  (Subjects) attributes.get("subject"),
                  (String) attributes.get("workingHours"));
          dao.<Teacher>save(teacher, "T");
     }
     public static void createStudent(Map attributs) throws IOException {
          Student student = new Student((int) attributs.get("id"),
                  (String) attributs.get("fullname"),
                  (int) attributs.get("birthYear"),
                  (String) attributs.get("phoneNumber"),
                  (Map) attributs.get("averageMarks"),
                  (Subjects[]) attributs.get("subjects"));
          dao.<Student>save(student, "S");
     }

     public static void edit(Map attributes) throws IOException {
          int id = (int)attributes.get("id");
          String type = dao.determineClass(id);
          if (type == null) return;
          if (type.equals("T")) {
               Teacher teacher = dao.<Teacher>get(id, type);
               if (attributes.containsKey("fullname")) teacher.setFullName((String) attributes.get("fullname"));
               if (attributes.containsKey("birthYear")) teacher.setBirthYear((int) attributes.get("birthYear"));
               if (attributes.containsKey("phoneNumber")) teacher.setPhoneNumber((String) attributes.get("phoneNumber"));
               if (attributes.containsKey("subject")) teacher.setSubject((Subjects) attributes.get("subject"));
               if (attributes.containsKey("workingHours")) teacher.setWorkingHours((String) attributes.get("workingHours"));
               dao.<Teacher>update(teacher, "T");
          } else {
               Student student = dao.<Student>get(id, type);
               if (attributes.containsKey("fullname")) student.setFullName((String) attributes.get("fullname"));
               if (attributes.containsKey("birthYear")) student.setBirthYear((int) attributes.get("birthYear"));
               if (attributes.containsKey("phoneNumber")) student.setPhoneNumber((String) attributes.get("phoneNumber"));
               if (attributes.containsKey("averageMarks")) student.setAverageMarks((Map) attributes.get("averageMarks"));
               if (attributes.containsKey("subjects")) student.setSubjects((Subjects[]) attributes.get("subjects"));
               dao.<Student>update(student, "S");
          }
     }

     public static void delete(int id) {
          String type = dao.determineClass(id);
          if (type == null) return;
          dao.delete(id, type);
     }

}
