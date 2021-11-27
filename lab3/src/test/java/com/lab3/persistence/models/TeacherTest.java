package com.lab3.persistence.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    Teacher teacher;

    @BeforeEach
    void initialisation() throws IOException {
        teacher = new Teacher("Ivanov Ivan Ivanovich",
                1990,
                89991231212L,
                Subjects.coding,
                "10:00-11:00");
    }

    @Test
    void setSubject() throws IOException {
        teacher.setSubject(Subjects.math);
        Teacher anotherTeacher = new Teacher("Ivanov Ivan Ivanovich",
                1990,
                89991231212L,
                Subjects.math,
                "10:00-11:00");
        assertEquals(anotherTeacher, teacher);
    }

    @Test
    void setWorkingHours() throws IOException {
        teacher.setWorkingHours("12:00-13:00");
        Teacher anotherTeacher = new Teacher("Ivanov Ivan Ivanovich",
                1990,
                89991231212L,
                Subjects.coding,
                "12:00-13:00");
        assertEquals(anotherTeacher, teacher);

        Exception exception = assertThrows(IOException.class, () -> {
            teacher.setWorkingHours("123");
        });
        assertEquals("Поле введено некорректно", exception.getMessage());
    }
}