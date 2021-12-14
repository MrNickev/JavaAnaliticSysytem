import java.util.Calendar;

public abstract class Person {
    private String name;
    private String surname;
    private Calendar birthdate;
    private String city;
    private int id;
    private String photoUrl;
    private String sex;

    public Person(int id, String name, String surname, Calendar birthdate, String city, String photoURL, String sex) {
        this.birthdate = birthdate;
        if (city != null)
            this.city = city.substring(1, city.length()-1);
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.photoUrl = photoURL;
        this.sex = sex;
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhototUrl(String photoUrl) {
        if (!(photoUrl == null || photoUrl.equals("")))
            this.photoUrl = photoUrl;
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
        if (birthdate == null) return null;
        return String.format("%d-%d-%d", birthdate.get(Calendar.YEAR), birthdate.get(Calendar.MONTH), birthdate.get(Calendar.DATE));
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
