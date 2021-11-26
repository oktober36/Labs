package com.lab3.service;

import com.lab3.dao.CachedPeopleDAO;
import com.lab3.persistence.models.Subjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PeopleService {
    private CachedPeopleDAO dao = new CachedPeopleDAO();

    private void checkExcessAttributes(Map<String, Object> attributes) throws IOException {
        List<String> necessaryAttributes = null;
        if (attributes.get("type").equals("student")){
            necessaryAttributes = Arrays.asList(
                    "id",
                    "type",
                    "fullname",
                    "birth year",
                    "phone number",
                    "average marks");
        } else if (attributes.get("type").equals("teacher")) {
            necessaryAttributes = Arrays.asList(
                    "id",
                    "type",
                    "fullname",
                    "birth year",
                    "phone number",
                    "subject",
                    "working hours");
        }
        for (String attribute : attributes.keySet()){
            if (!necessaryAttributes.contains(attribute)) {
                throw new IOException("Вы ввели лишнее поле");
            }
        }
    }

    private int extractId(Map<String, Object> attributes) throws IOException {
        if (!attributes.containsKey("id")) throw new IOException("Вы забыли ввести поле");
        int id;
        try {
            id = (int) attributes.get("id");
        }
        catch (ClassCastException e) {
            throw new IOException("Поле введено некорректно");
        }
        if (attributes.size() > 1) throw new IOException("Вы ввели лишнее поле");
        return id;
    }

    private void determineType(Map<String, Object> attributes) throws IOException {
        if (attributes.containsKey("type")) throw new IOException("Вы ввели лишнее поле");
        if (attributes.containsKey("average marks")) {
            attributes.put("type", "student");
        } else if (attributes.containsKey("subject")) {
            attributes.put("type", "teacher");
        }  else throw new IOException("Вы забыли ввести поле");
    }

    public void create(Map<String, Object> attributes) throws IOException {
        determineType(attributes);
        checkExcessAttributes(attributes);
        if (attributes.containsKey("id")) throw new IOException("Вы ввели лишнее поле");
        dao.save(attributes);
    }

    public void update(Map<String, Object> attributes) throws IOException {
        if (!attributes.containsKey("id")) throw new IOException("Вы забыли ввести поле");
        int id;
        try {
            id = (int) attributes.get("id");
        } catch (ClassCastException e) {
            throw new IOException("Поле введено некорректно");
        }
        String type = (dao.determineType(id).equals("S"))? "student" : "teacher";
        attributes.put("type", type);
        checkExcessAttributes(attributes);
        dao.update(attributes);
    }

    public void delete(Map<String, Object> attributes) throws IOException {
        int id = extractId(attributes);
        dao.delete(id);
    }

    public String get(Map<String, Object> attributes) throws IOException {
        int id = extractId(attributes);
        return dao.get(id).toString();
    }

    @Override
    public String toString() {
        return dao.toString();
    }
}
