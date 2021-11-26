package com.lab3.dao;

import com.lab3.persistence.models.Student;
import com.lab3.persistence.models.Subjects;
import com.lab3.persistence.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CachedPeopleDAOTest {

    Map<String, Object> studentAttributes;
    Map<String, Object> teacherAttributes;
    CachedPeopleDAO dao = new CachedPeopleDAO();
    Exception exception;

    @BeforeEach
    void clear(){
        dao = new CachedPeopleDAO();
        studentAttributes = new HashMap<>();
        studentAttributes.putAll(Map.of("type",
                "student",
                "fullname",
                "Ivanov Ivan Ivanovich",
                "birth year",
                1990,
                "phone number",
                89991231212L,
                "average marks",
                Map.of("coding",  5.00)));
        teacherAttributes = new HashMap<>();
        teacherAttributes.putAll(Map.of("type",
                "teacher",
                "fullname",
                "Petrov Ivan Ivanovich",
                "birth year",
                1980,
                "phone number",
                89991231213L,
                "subject",
                "coding",
                "working hours",
                "8:30-16:00"));
    }

    @Test
    void save() throws IOException {
        //Тестируем, что сохранение студента проходит
        dao.save(studentAttributes);
        Student student = new Student("Ivanov Ivan Ivanovich",
                1990,
                89991231212L,
                Map.of(Subjects.coding, 5.00F));
        assertEquals(student, dao.get(0));

        //Тестируем, что сохранение учителя проходит
        dao.save(teacherAttributes);

        Teacher teacher = new Teacher("Petrov Ivan Ivanovich",
                1980,
                89991231213L,
                Subjects.coding,
                "8:30-16:00");
        assertEquals(teacher, dao.get(1));
    }

    @Test
    void saveExceptions() {
        exception = assertThrows(IOException.class, () -> {
            teacherAttributes.put("working hours", "allday");
            dao.save(teacherAttributes); //некорректное поле
        });
        assertEquals("Поле введено некорректно", exception.getMessage());

        //Проверяем экзепшн при утери поля

        exception = assertThrows(IOException.class, () -> {
            studentAttributes.remove("average marks");
            dao.save(studentAttributes);
        });
        assertEquals("Вы забыли ввести поле", exception.getMessage());
    }


    @Test
    void update() throws IOException {
        //Тестируем, что обновение студента проходит
        dao.save(studentAttributes);
        Map<String, Object> studentUpdateAttributes = new HashMap<>(Map.of("id",
                0,
                "fullname",
                "Ivanov Jovan Ivanovich",
                "birth year",
                1991,
                "phone number",
                81234560000L,
                "average marks",
                Map.of("math", 3.50)));
        dao.update(studentUpdateAttributes);

        Student student = new Student("Ivanov Jovan Ivanovich",
                1991,
                81234560000L,
                Map.of(Subjects.math, 3.50F));
        assertEquals(student, dao.get(0));

        //Тестируем, что обновение учителя проходит
        Map<String, Object> teacherUpdateAttributes = new HashMap<>(Map.of("id",
                1,
                "type",
                "teacher",
                "fullname",
                "Petrov Petr Ivanovich",
                "birth year",
                1981,
                "phone number",
                81234560001L,
                "subject",
                "math",
                "working hours",
                "8:30-17:00"));

        dao.save(teacherAttributes);
        dao.update(teacherUpdateAttributes);

        Teacher teacher = new Teacher("Petrov Petr Ivanovich",
                1981,
                81234560001L,
                Subjects.math,
                "8:30-17:00");
        assertEquals(teacher, dao.get(1));
    }

    @Test
    void updateExceptions() {
        //Проверяем экзепшн при вводе несуществующего айди
        exception = assertThrows(IOException.class, () -> {
            Map<String, Object> teacherWrongUpdateAttributes = new HashMap<>(Map.of("id",
                    5,
                    "fullname",
                    "Petrov Petr Ivanovich",
                    "birth year",
                    1981,
                    "phone number",
                    81234560001L,
                    "subject",
                    "math",
                    "working hours",
                    "8:30-17:00"));
            dao.save(teacherAttributes);
            dao.update(teacherWrongUpdateAttributes);
        });
        assertEquals("Человека с таким айди не существет", exception.getMessage());

        //Проверяем экзепшн при вводе неоррекетного поля
        exception = assertThrows(IOException.class, () -> {
            Map<String, Object> studentWrongUpdateAttributes = new HashMap<>(Map.of("id",
                    1,
                    "fullname",
                    "Petrov Petr Ivanovich",
                    "birth year",
                    "zeros",
                    "phone number",
                    81234560001L,
                    "average marks",
                    Map.of("coding", 5.00)));
            dao.save(studentAttributes);
            dao.update(studentWrongUpdateAttributes);
        });
        assertEquals("Поле введено некорректно", exception.getMessage());

    }

    @Test
    void delete() throws IOException {
        //Тестируем, что удаление персоны проходит
        dao.save(studentAttributes);
        dao.delete(0);
        Exception exception = assertThrows(IOException.class, () -> dao.get(0));
        assertEquals("Человека с таким айди не существет", exception.getMessage());

        //Проверяем экзепшн при вводе несуществующего айди
        exception = assertThrows(IOException.class, () -> dao.delete(5));
        assertEquals("Человека с таким айди не существет", exception.getMessage());
    }


    @Test
    void get() {
        // Так как мы проверили что get() выводит верных персонов, проверим экзепшн
        Exception exception = assertThrows(IOException.class, () -> dao.get(5));
        assertEquals("Человека с таким айди не существет", exception.getMessage());
    }

    @Test
    void determineType() throws IOException {
        //Проверим вывод типа для учителя и ученика
        dao.save(studentAttributes);
        assertEquals("S", dao.determineType(0));

        dao.save(teacherAttributes);
        assertEquals("T", dao.determineType(1));

        //Проверим экзепшн
        Exception exception = assertThrows(IOException.class, () -> dao.determineType(5));
        assertEquals("Человека с таким айди не существет", exception.getMessage());

    }
}