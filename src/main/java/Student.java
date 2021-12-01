import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Student extends Person {
    private ArrayList<Subject> subjects;


    public Student(int id, String name, String surname, String city, Calendar birthdate) {
        super(id, name, surname, birthdate, city);
        subjects = new ArrayList<>();
    }

    public Student(String name, String surname) {
        super(name, surname);
        subjects = new ArrayList<>();
    }

    public void addInfo(Student student) {
        if (getId() == 0 && student.getId() != 0)
            setId(student.getId());
        if (getCity() == null && student.getCity() != null)
            setCity(student.getCity());
        if (getBirthdate() == null && student.getBirthdate() != null)
            setBirthdate(student.getBirthdate());
        if (subjects.isEmpty() && !student.getSubject().isEmpty())
            addSubjects(student.getSubject());

    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void addSubjects(List<Subject> subjects) { for (var subject : subjects) this.subjects.add(subject);}

    public List<Subject> getSubject() {
        return subjects;
    }

    public String toString(InfoType infoType) {
        switch (infoType){
            case AllInfo:
                return String.format("%s %s \t %s", super.getName(), super.getSurname(), super.getStringBirthdate(), convertSubjectsArrayToString());

            case PrivateInfo:
                return String.format("%d %s %s \t %s \t %s", super.getId(), super.getName(), super.getSurname(), super.getStringBirthdate(), super.getCity());
        }
        return String.format("%s %s \t %s", super.getName(), super.getSurname(), convertSubjectsArrayToString());
    }


    private String convertSubjectsArrayToString() {
        var sb = new StringBuilder();
        for (var subject : subjects)
            sb.append(subject.toString() + "\n");
        return sb.toString();
    }

}

