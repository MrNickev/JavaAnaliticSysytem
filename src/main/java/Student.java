import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Student extends Person {

    private ArrayList<Subject> subjects;

    public Student(int id, String name, String surname, String city, Calendar birthdate, String photoURL, String sex) {
        super(id, name, surname, birthdate, city, photoURL, sex);
        subjects = new ArrayList<>();
    }

    public Student(String name, String surname) {
        super(name, surname);
        subjects = new ArrayList<>();
    }

    public void addInfo(Student student) {
        if (getId() == 0 && student.getId() != 0)
            setId(student.getId());
        if ((getCity() == null || !getCity().equals("")) && student.getCity() != null)
            setCity(student.getCity());
        if (getBirthdate() == null && student.getBirthdate() != null)
            setBirthdate(student.getBirthdate());
        if (subjects.isEmpty() && !student.getSubject().isEmpty())
            addSubjects(student.getSubject());
        if ((getPhotoUrl() == null || getPhotoUrl().equals("")))
            setPhototUrl(student.getPhotoUrl());
        if ((getSex() == null || getSex().equals("")))
            setSex(student.getSex());
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
                return String.format("%s %s \t %s \t %s \t %s \t %s", super.getName(), super.getSurname(), super.getStringBirthdate(), super.getPhotoUrl(), super.getCity(), convertSubjectsArrayToString());

            case PrivateInfo:
                return String.format("%d %s %s \t %s \t %s", super.getId(), super.getName(), super.getSurname(), super.getStringBirthdate(), super.getCity());

            case PrivateWithPhoto:
                return String.format("%d %s %s \t %s \t %s \t %s", super.getId(), super.getName(), super.getSurname(), super.getStringBirthdate(), super.getCity(), super.getPhotoUrl());

            case SubjectInfo:
                return String.format("%s %s \t %s", super.getName(), super.getSurname(), convertSubjectsArrayToString());
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

