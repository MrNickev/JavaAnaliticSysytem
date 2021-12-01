import java.util.Calendar;

public abstract class Person {
    private String name;
    private String surname;
    private Calendar birthdate;
    private String city;
    private int id;

    public Person(int id, String name, String surname, Calendar birthdate, String city) {
        this.birthdate = birthdate;
        this.city = city.substring(1, city.length()-1);
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getSurname() {
        return surname;
    }

    public String getStringBirthdate() {
        if (birthdate == null) return "None";
        return String.format("%d.%d.%d", birthdate.get(Calendar.DATE), birthdate.get(Calendar.MONTH), birthdate.get(Calendar.YEAR));
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public String getCity() {
        if (city == null) return "None";
            return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBirthdate(Calendar birthdate) { this.birthdate = birthdate; }
}
