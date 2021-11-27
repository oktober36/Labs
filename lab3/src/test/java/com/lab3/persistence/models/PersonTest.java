package com.lab3.persistence.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    Student student;

    @BeforeEach
    void initialisation() throws IOException {
        student = new Student("Ivanov Ivan Ivanovich",
                1990,
                89991231212L,
                Map.of(Subjects.coding, 5.00F));
    }

    @Test
    void setFullName() throws IOException {
        student.setFullName("Ivanov Jovan Ivanovich");
        Student anotherStudent= new Student("Ivanov Jovan Ivanovich",
                1990,
                89991231212L,
                Map.of(Subjects.coding, 5.00F));
        assertEquals(anotherStudent, student);

        Exception exception = assertThrows(IOException.class, () -> {
            student.setFullName("123");
        });
        assertEquals("Поле введено некорректно", exception.getMessage());

        assertThrows(NullPointerException.class, () -> {
            student.setFullName(null);
        });
    }

    @Test
    void setBirthYear() throws IOException {
        student.setBirthYear(2000);
        Student anotherStudent= new Student("Ivanov Ivan Ivanovich",
                2000,
                89991231212L,
                Map.of(Subjects.coding, 5.00F));
        assertEquals(anotherStudent, student);

        Exception exception = assertThrows(IOException.class, () -> {
            student.setBirthYear(3000);
        });
        assertEquals("Поле введено некорректно", exception.getMessage());
    }

    @Test
    void setPhoneNumber() throws IOException {
        student.setPhoneNumber(82283221337L);
        Student anotherStudent= new Student("Ivanov Ivan Ivanovich",
                1990,
                82283221337L,
                Map.of(Subjects.coding, 5.00F));
        assertEquals(anotherStudent, student);

        Exception exception = assertThrows(IOException.class, () -> {
            student.setPhoneNumber(3000l);
        });
        assertEquals("Поле введено некорректно", exception.getMessage());
    }
}