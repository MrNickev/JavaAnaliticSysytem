import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Student extends Person {
    private ArrayList<Subject> subjects;


    public Student(String name, String surname, String city, Calendar birthdate) {
        super(name, surname, birthdate, city);
        subjects = new ArrayList<>();
    }

    public Student(String name, String surname) {
        super(name, surname);
        subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public List<Subject> getSubject() {
        return subjects;
    }

    public String toString() {
        return String.format("%s %s \t %s", super.getName(), super.getSurname(), convertSubjectsArrayToString());
    }


    private String convertSubjectsArrayToString() {
        var sb = new StringBuilder();
        for (var subject : subjects)
            sb.append(subject.toString() + "\n");
        return sb.toString();
    }

}

