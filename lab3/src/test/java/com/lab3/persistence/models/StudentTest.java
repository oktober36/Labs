package com.lab3.persistence.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student student;

    @BeforeEach
    void initialisation() throws IOException {
        student = new Student("Ivanov Ivan Ivanovich",
                1990,
                89991231212L,
                Map.of(Subjects.coding, 5.00F));
    }

    @Test
    void setAverageMarks() throws IOException {
        student.setAverageMarks(Map.of(Subjects.math, 3.00F));
        Student anotherStudent= new Student("Ivanov Ivan Ivanovich",
                1990,
                89991231212L,
                Map.of(Subjects.math, 3.00F));
        assertEquals(anotherStudent, student);

        Exception exception = assertThrows(IOException.class, () -> {
            student.setAverageMarks(Map.of(Subjects.math, 21F));
        });
        assertEquals("Поле введено некорректно", exception.getMessage());
    }
}