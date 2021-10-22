package peopleClasses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Student extends Person{
    private Map averageMarks = new HashMap();
    private Subjects[] subjects;

    private Student() {
        super(0, null, 0,null);
    }

    public Student(int id, String fullName, int birthYear, String phoneNumber,
                      Map averageMarks, Subjects[] subjects) {
        super(id, fullName, birthYear, phoneNumber);
        this.averageMarks = averageMarks;
        this.subjects = subjects;
    }

    public Map getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(Map averageMarks) {
        this.averageMarks = averageMarks;
    }

    public Subjects[] getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects[] subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() +
                ", averageMarks=" + averageMarks +
                ", subjects=" + Arrays.toString(subjects) +
                '}';
    }
}