package DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import mainClasses.*;
import java.io.File;
import java.io.IOException;

public class PeopleDAO implements Dao {
    String filePath;

    public PeopleDAO(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public <T extends Person> void save(T person, String type) throws IOException {
        if (!(determineClass(person.getId()) == null))  return;
        ObjectMapper objectMapper = new ObjectMapper();
        String filename = person.getId() + type + ".json";
        File file = new File(filePath + filename);
        objectMapper.writeValue(file, person);
    }

    @Override
    public <T extends Person> void update(T person, String type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String filename = person.getId() + type + ".json";
        File file = new File(filePath + filename);
        file.delete();
        objectMapper.writeValue(new File(filePath + filename), person);
    }

    @Override
    public void delete(int id, String type) {
        String filename = id + type + ".json";
        File file = new File(filePath + filename);
        file.delete();

    }

    @Override
    public <T extends Person> T get(int id, String type) throws IOException {
        String filename = id + type + ".json";
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath + filename);
        if (type.equals("T")) {
            Teacher person = objectMapper.readValue(file, Teacher.class);
            return (T) person;
        } else {
            Student person = objectMapper.readValue(file, Student.class);
            return (T) person;
        }

    }

    @Override
    public String determineClass(int id) {
        File file = new File(filePath + id + "T.json");
        if (file.exists()) {
            return "T";
        } else {
            file = new File(filePath + id + "S.json");
            if (file.exists()) {
                return "S";
            }
        }
        return null;
    }
}
