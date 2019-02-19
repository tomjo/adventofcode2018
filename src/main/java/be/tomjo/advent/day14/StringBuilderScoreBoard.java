package be.tomjo.advent.day14;

public class StringBuilderScoreBoard implements ScoreBoard {

    private final StringBuilder stringBuilder;

    public StringBuilderScoreBoard(int... scores) {
        stringBuilder = new StringBuilder();
        add(scores);
    }

    @Override
    public void add(int... scores) {
        for (int score : scores) {
            stringBuilder.append(score);
        }
    }

    @Override
    public int getScoreCount() {
        return stringBuilder.length();
    }

    @Override
    public int getScoreAt(int position) {
        return stringBuilder.charAt(position) - '0';
    }

    @Override
    public int locationOfScoreSequence(String recipeScoreSequence) {
        return stringBuilder.indexOf(recipeScoreSequence);
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
