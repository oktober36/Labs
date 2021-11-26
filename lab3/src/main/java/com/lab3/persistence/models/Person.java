package com.lab3.persistence.models;

import java.io.IOException;
import java.time.Year;
import java.util.regex.Pattern;

public class Person {
    protected String fullName;
    protected Year birthYear;
    protected Long phoneNumber;

    Person(String fullName, Integer birthYear, Long phoneNumber) throws IOException {
        if (!Pattern.matches("([A-Z][a-z]* ){2}[A-Z][a-z]*", fullName)) {
            throw new IOException("Поле введено некорректно");
        }
        if (fullName == null) throw new NullPointerException();
        if (1900 > birthYear || 2020 < birthYear) {
            throw new IOException("Поле введено некорректно");
        }
        if (80000000000L > phoneNumber || 89999999999L < phoneNumber) {
            throw new IOException("Поле введено некорректно");
        }
        this.fullName = fullName;
        this.birthYear = Year.of(birthYear);
        this.phoneNumber = phoneNumber;
    }

    public void setFullName(String fullName) throws IOException {
        if (!Pattern.matches("([A-Z][a-z]* ){2}[A-Z][a-z]*", fullName)) {
            throw new IOException("Поле введено некорректно");
        }
        if (fullName == null) throw new NullPointerException();
        this.fullName = fullName;
    }

    public void setBirthYear(Integer birthYear) throws IOException {
        if (1900 > birthYear || 2020 < birthYear) {
            throw new IOException("Поле введено некорректно");
        }
        this.birthYear = Year.of(birthYear);
    }

    public void setPhoneNumber(Long phoneNumber) throws IOException {
        if (80000000000L > phoneNumber || 89999999999L < phoneNumber) {
            throw new IOException("Поле введено некорректно");
        }
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return  "fullName=" + fullName +
                ", birthYear=" + birthYear +
                ", phoneNumber=" + phoneNumber;
    }
}
