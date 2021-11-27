package com.lab3.persistence.models;

import java.io.IOException;
import java.util.*;

public class Student extends Person{
    private Map<Subjects, Float> averageMarks;
    private List<Subjects> subjects;

    public Student(String fullName, Integer birthYear, Long phoneNumber,
                   Map<Subjects, Float> averageMarks) throws IOException {
        super(fullName, birthYear, phoneNumber);

        for (Float mark : averageMarks.values()){
            if (mark < 2.00F || mark > 5.00F) throw new IOException("Поле введено некорректно");
        }

        if (averageMarks == null) {
            throw new NullPointerException();
        }
        this.averageMarks = averageMarks;
        this.subjects = new ArrayList<>(averageMarks.keySet());
    }

    public void setAverageMarks(Map<Subjects, Float> averageMarks) throws IOException {
        for (Float mark : averageMarks.values()){
            if (mark < 2.00F || mark > 5.00) throw new IOException("Поле введено некорректно");
        }
        if (averageMarks == null) throw new NullPointerException();
        this.averageMarks = averageMarks;
        this.subjects = new ArrayList<>(averageMarks.keySet());
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() +
                ", averageMarks=" + averageMarks +
                ", subjects=" + subjects.toString() +
                '}';
    }

    @Override
    public boolean equals(Object student){
        if (student == null) return false;
        if (student.getClass() == Student.class){
            if (this.fullName.equals(((Student) student).fullName)
                    && this.birthYear.equals(((Student) student).birthYear)
                    && this.phoneNumber.equals(((Student) student).phoneNumber))
            {
                if (subjects.size() != ((Student) student).subjects.size()) return false;
                for (Subjects subject : subjects){
                    if (!((Student) student).subjects.contains(subject)) return false;
                    if (!((Student) student).averageMarks.get(subject).equals(averageMarks.get(subject))) return false;
                }
            }
            return true;
        }
        return false;
    }
}