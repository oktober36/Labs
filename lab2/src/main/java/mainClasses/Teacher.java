package mainClasses;

public class Teacher extends Person{
    private Subjects subject;
    private String workingHours;

    private Teacher(){
        super(0, null, 0, null);
    }
    public Teacher(int id, String fullName, int birthYear, String phoneNumber, Subjects subject, String workingHours) {
        super(id, fullName, birthYear, phoneNumber);
        this.subject = subject;
        this.workingHours = workingHours;
    }

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString() +
                ", subject=" + subject +
                ", workingHours='" + workingHours + '\'' +
                '}';
    }
}
