import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Theme {
    private String name;
    private Integer[] scores;
    private int totalScore;
    private int scoresCount;

    public Theme(String name, int scoresCount) {
        this.scoresCount = scoresCount;
        this.name = name;
        scores = new Integer[1];
    }

    public void setScores(Integer[] scores) {
        this.scores = scores;
    }


    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public boolean areEquals(Theme theme) {
        return (name == theme.name && scoresCount == theme.scoresCount);

    }

    public String toString() {
        return name + " " + totalScore;
    }

    public String getName() {
        return name;
    }

    public Integer[] getScores() {
        return scores;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getScoresCount() {
        return scoresCount;
    }

}
