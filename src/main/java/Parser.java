import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Parser {

    public static ArrayList<Student> ParseCSVFile(String filepath) throws IOException {
        List<String> fileLines = Files.readAllLines(Paths.get(filepath));
        var themes = new ArrayList<Theme>();
        var students = new ArrayList<Student>(fileLines.size()-1);
        var lineIndex = 0;
        for (var line : fileLines) {
            themes = parseThemesRow(fileLines.get(0));
            if (lineIndex > 2){
                var parsedRow = parseRowToArray(line);
                var studentNames = parsedRow.get(0).split(" ");
                var student = new Student(studentNames[1], studentNames[0]);
                var groupName = parsedRow.get(1);

                parsedRow.remove(0);
                parsedRow.remove(1);

                var scores = new ArrayList<Integer>();
                parsedRow.forEach(s -> {if (isNumeric(s)) scores.add(Integer.parseInt(s));});
                addScoresToThemes(themes, scores);
                student.addSubject(new Subject("Java", groupName, themes));
                students.add(student);
            }
            lineIndex++;
        }

        return students;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static ArrayList<String> parseRowToArray(String str) {
        return new ArrayList<>(Arrays.asList(str.split(";")));
    }

    public static ArrayList<Theme> parseThemesRow(String str) {
        var list = new ArrayList<Theme>();
        var splitted = str.split(";", -1);
        String themeName = "";
        var scoresCount = 0;
        for (var i = 3; i < splitted.length; i++) {
            if (!splitted[i].equals("") || i == splitted.length - 1) {
                if (!(themeName.equals("")))
                    list.add(new Theme(themeName, scoresCount));
                themeName = splitted[i];
                scoresCount = 0;
            }
            else scoresCount++;
        }

        return list;
    }

    public static void addScoresToThemes(List<Theme> themes, List<Integer> scores) {
        var index = 0;
        for (var theme : themes) {
            theme.setTotalScore(scores.get(index).intValue());
            index++;
            theme.setScores(scores.subList(index, index + theme.getScoresCount()).toArray(new Integer[0]));
            index += theme.getScoresCount();
        }
    }
}
