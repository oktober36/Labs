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
     private final static PeopleDAO dao = new PeopleDAO("src/main/resources/PersonsFiles/");


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
          deleteId(id);
          dao.delete(id, type);
     }

     private static int nextId() throws IOException {
          int returnedId = -1;
          File file = new File("src/main/resources/PersonsFiles/ids.txt");
          ObjectMapper mapper = new ObjectMapper();
          ArrayList<Integer> ids;
          if (new File("src/main/resources/PersonsFiles").listFiles().length == 1) {
               ids = new ArrayList<>();
          }
          else {
               ids = mapper.readValue(file, ArrayList.class);
          }
          if (ids.size() == 0){
               ids.add(0);
               returnedId = 0;
          }
          for (int i = 0; i < ids.size(); i++) {
               if (ids.get(i) != i) {
                         returnedId = i;
                         ids.add(i, i);
               }
          }
          if (returnedId == -1) {
               returnedId = ids.size();
               ids.add(ids.size());
          }
          mapper.writeValue(file, ids);
          return returnedId;
          }
     private static void deleteId(int id) throws IOException {
          File file = new File("src/main/resources/PersonsFiles/ids.txt");
          ObjectMapper mapper = new ObjectMapper();
          ArrayList<Integer> ids = null;
          ids = mapper.readValue(file, ArrayList.class);
          ids.remove((Integer) id);
          mapper.writeValue(file, ids);
     }


}
