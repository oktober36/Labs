package com.lab3.service;

import com.lab3.dao.CachedPeopleDAO;
import com.lab3.persistence.models.Student;
import com.lab3.persistence.models.Subjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PeopleServiceTest {
    PeopleService peopleService;

    @BeforeEach
    void clearService(){
        peopleService = new PeopleService();
    }

    @Test
    void create() throws IOException {
        // Проверим работоспособность метода, заодно проверим и get()
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.putAll(Map.of("fullname",
                "Ivanov Ivan Ivanovich",
                "birth year",
                1990,
                "phone number",
                89991231212L,
                "average marks",
                Map.of(Subjects.coding, 5.00F)));

        peopleService.create(attributes);
        Student student = new Student("Ivanov Ivan Ivanovich",
                1990,
                89991231212L,
                Map.of(Subjects.coding, 5.00F));
        assertEquals(student.toString(), peopleService.get(Map.of("id", 0)));

        // Проверим экзепшны
        Exception exception = assertThrows(IOException.class, () -> {
            attributes.clear();
            attributes.putAll(Map.of("fullname",
                    "Ivanov Ivan Ivanovich",
                    "birth year",
                    1990,
                    "phone number",
                    89991231212L,
                    "average marks",
                    Map.of(Subjects.coding, 5.00F),
                    "MAMA",
                    "MAMA"));
            peopleService.create(attributes);
        });
        assertEquals("Вы ввели лишнее поле", exception.getMessage());

        exception = assertThrows(IOException.class, () -> {
            attributes.clear();
            attributes.putAll(Map.of("fullname",
                    "Ivanov Ivan Ivanovich",
                    "birth year",
                    1990,
                    "phone number",
                    89991231212L));
            peopleService.create(attributes);
        });
        assertEquals("Вы забыли ввести поле", exception.getMessage());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void get() {
    }
}