import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Subject {
    private String name;
    private ArrayList<Theme> themes;
    private String groupName;

    public Subject(String name, String groupNumber, ArrayList<Theme> themes) {
        this.name = name;
        this.themes = themes;
        this.groupName = groupNumber;
    }
    @Override
    public String toString() {
        return (String.format("%s \t %s \t %s", name, groupName, getThemesString()));
    }

    public void updateScores() {

    }

    private String getThemesString() {
        var sb = new StringBuilder();
        for (var theme : themes) {
            sb.append(theme.toString());
            sb.append(", ");
        }

        return sb.toString();
    }

//    private String convertScoresArrayToString() {
//        var str = new StringBuilder();
//        for (var score : scores) {
//            str.append(score + " ");
//        }
//        return str.toString();
//    }

}
