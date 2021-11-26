package com.lab3.persistence.models;


import java.io.IOException;
import java.util.regex.Pattern;

public class Teacher extends Person{
    private Subjects subject;
    private String workingHours;

    public Teacher(String fullName,
                   Integer birthYear,
                   Long phoneNumber,
                   Subjects subject,
                   String workingHours) throws IOException {
        super(fullName, birthYear, phoneNumber);

        if (workingHours == null) throw new NullPointerException();
        if (subject == null) throw new NullPointerException();

        if (!Pattern.matches("((2[0-4])|(1?[0-9])):[0-5]\\d-((2[0-4])|(1?[0-9])):[0-5]\\d", workingHours)){
            throw new IOException("Поле введено некорректно");
        }
        this.subject = subject;
        this.workingHours = workingHours;
    }

    public void setSubject(Subjects subject) {
        if (subject == null) throw new NullPointerException();
        this.subject = subject;
    }

    public void setWorkingHours(String workingHours) throws IOException {
        if (workingHours == null) throw new NullPointerException();
        if (!Pattern.matches("((2[0-4])|(1?[0-9])):[0-5]\\d-((2[0-4])|(1?[0-9])):[0-5]\\d", workingHours)){
            throw new IOException("Поле введено некорректно");
        }
        this.workingHours = workingHours;
    }

    @Override
    public String toString() {
        return "Teacher {" + super.toString() +
                ", subject=" + subject +
                ", workingHours='" + workingHours + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object teacher){
        if (teacher == null) return false;
        if (teacher.getClass() == Teacher.class){
            if (this.fullName.equals(((Teacher) teacher).fullName)
                    && this.birthYear.equals(((Teacher) teacher).birthYear)
                    && this.phoneNumber.equals(((Teacher) teacher).phoneNumber)
                    && this.subject.equals(((Teacher) teacher).subject)
                    && this.workingHours.equals(((Teacher) teacher).workingHours))
                return true;
        }
        return false;
    }
}
