import java.util.Calendar;
import java.util.Date;

public abstract class Person {
    private String name;
    private String surname;
    private Calendar birthdate;
    private String city;

    public Person(String name, String surname, Calendar birthdate, String city) {
        this.birthdate = birthdate;
        this.city = city;
        this.name = name;
        this.surname = surname;
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
