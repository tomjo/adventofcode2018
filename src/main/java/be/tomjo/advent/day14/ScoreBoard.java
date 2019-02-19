package be.tomjo.advent.day14;

public interface ScoreBoard {
    void add(int... scores);

    int getScoreCount();

    int getScoreAt(int elfPosition);

    int locationOfScoreSequence(String recipeScoreSequence);
}
