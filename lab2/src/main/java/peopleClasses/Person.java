package peopleClasses;

public class Person {
    private String fullName, phoneNumber;
    private int birthYear;
    private final int id;

    protected Person(int id, String fullName, int birthYear, String phoneNumber){
        this.id = id;
        this.fullName = fullName;
        this.birthYear = birthYear;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return  "fullName='" + fullName + '\'' +
                ", birthYear=" + birthYear +
                ", phoneNumber=" + phoneNumber +
                ", id=" + id;
    }
}
