package com.lab3.dao;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.lab3.persistence.models.Student;
import com.lab3.persistence.models.Subjects;
import com.lab3.persistence.models.Teacher;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class CachedPeopleDAO {
    private final Map<String, Object> persons = new HashMap<>();
    private final LinkedList<Integer> ids = new LinkedList<>();

    public void save(Map<String, Object> attributes) throws IOException {
        int id = generateId();
        try {
            if (attributes.get("type").equals("teacher")) {
                persons.put(id + "T",
                        new Teacher((String) attributes.get("fullname"),
                                (Integer) attributes.get("birth year"),
                                (Long) attributes.get("phone number"),
                                Subjects.valueOf((String) attributes.get("subject")),
                                (String) attributes.get("working hours")));

            } else if (attributes.get("type").equals("student")) {
                convertAverageMarks(attributes);
                persons.put(id + "S",
                        new Student((String) attributes.get("fullname"),
                                (Integer) attributes.get("birth year"),
                                (Long) attributes.get("phone number"),
                                (Map<Subjects, Float>) attributes.get("average marks")));
            }
        } catch (NullPointerException e) {
            throw new IOException("Вы забыли ввести поле");
        } catch (ClassCastException e) {
            throw new IOException("Поле введено некорректно");
        } catch (JsonProcessingException e) {
            throw new IOException("Неправильно задан json");
        } catch (IOException e) {
            ids.remove(ids.indexOf(id));
            throw e;
        }
    }

    public void update(Map<String, Object> attributes) throws IOException {
        int id = (Integer) attributes.get("id");
        String type = determineType(id);
        try {
            if (type.equals("T")) {
                Teacher teacher = (Teacher) persons.get(id + "T");
                if (attributes.containsKey("fullname")) {
                    teacher.setFullName((String) attributes.get("fullname"));
                }
                if (attributes.containsKey("birth year")) {
                    teacher.setBirthYear((Integer) attributes.get("birth year"));
                }
                if (attributes.containsKey("phone number")) {
                    teacher.setPhoneNumber((Long) attributes.get("phone number"));
                }
                if (attributes.containsKey("subject")) {
                    teacher.setSubject((Subjects.valueOf((String)attributes.get("subject"))));
                }
                if (attributes.containsKey("working hours")) {
                    teacher.setWorkingHours((String) attributes.get("working hours"));
                }
                persons.put(id + "T", teacher);

            } else if (type.equals("S")) {
                Student student = (Student) persons.get(id + "S");
                if (attributes.containsKey("fullname")) {
                    student.setFullName((String) attributes.get("fullname"));
                }
                if (attributes.containsKey("birth year")) {
                    student.setBirthYear((int) attributes.get("birth year"));
                }
                if (attributes.containsKey("phone number")) {
                    student.setPhoneNumber((Long) attributes.get("phone number"));
                }
                if (attributes.containsKey("average marks")) {
                    convertAverageMarks(attributes);
                    student.setAverageMarks((Map<Subjects, Float>) attributes.get("average marks"));
                }
                persons.put(id + "S", student);
            }
        } catch (NullPointerException e) {
            throw new IOException("Вы забыли ввести поле");
        } catch (ClassCastException e) {
            throw new IOException("Поле введено некорректно");
        } catch (JsonProcessingException e) {
            throw new IOException("Неправильно задан json");
        }
    }

    public void delete(int id) throws IOException {
        persons.remove(id + determineType(id));
        ids.remove(ids.indexOf(id));
    }

    public <T> T get(int id) throws IOException {
        return (T) persons.get(id + determineType(id));
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (String id : persons.keySet()) {
            out.append(id.substring(0, id.length() - 1));
            out.append(" : ");
            out.append(persons.get(id).toString());
            out.append("\n");
        }
        return out.toString();
    }

    public String determineType(int id) throws IOException {
        if (persons.containsKey(id + "T")) return "T";
        else if (persons.containsKey(id + "S")) return "S";
        throw new IOException("Человека с таким айди не существет");
    }

    private int generateId() {
        int currentId = -1;
        if (ids.size() == 0) currentId = 0;
        else {
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i) != i) currentId = i;
            }
            if (currentId == -1) currentId = ids.size();
        }
        ids.add(currentId, currentId);
        return currentId;
    }

    private void convertAverageMarks(Map<String, Object> attributes) throws IOException {
        Map<Subjects, Float> averageMarks = new HashMap<Subjects, Float>();
        Map<String, Double> rawAverageMarks = ((Map<String, Double>) attributes.get("average marks"));
        for (String subject : rawAverageMarks.keySet()) {
            averageMarks.put(Subjects.valueOf(subject), rawAverageMarks.get(subject).floatValue());
        }
        attributes.remove("average marks");
        attributes.put("average marks", averageMarks);
    }
}
