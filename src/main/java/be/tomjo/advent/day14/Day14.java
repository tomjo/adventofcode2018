package be.tomjo.advent.day14;

import be.tomjo.advent.Solution;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Integer.parseInt;

public class Day14 implements Solution<String, Integer, String> {

    private static final int RECIPES_AFTER = 10;
    private static final int[] ELF_POSITIONS = {0, 1};
    private static final int[] ELF_STARTING_RECIPES = {3, 7};

    public static void main(String[] args) {
        new Day14().run();
    }

    @Override
    public String parse(String input) {
        return lines(input).get(0);
    }

    @Override
    public String part1(String recipeCountString) {
        int recipeCount = parseInt(recipeCountString);
        ScoreBoard scoreBoard = new StringBuilderScoreBoard(ELF_STARTING_RECIPES);
        RecipeCreator recipeCreator = new RecipeCreator(ELF_POSITIONS, ELF_STARTING_RECIPES);
        recipeCreator.createNewRecipesAndAddThemToScoreBoard(scoreBoard, recipeCount + RECIPES_AFTER);
        return scoreBoard.toString().substring(recipeCount, recipeCount + 10);
    }

    @Override
    public Integer part2(String recipeCountString) {
        int recipeCount = parseInt(recipeCountString);
        RecipeCreator recipeCreator = new RecipeCreator(ELF_POSITIONS, ELF_STARTING_RECIPES);
        ScoreBoard scoreBoard = new StringBuilderScoreBoard(ELF_STARTING_RECIPES);
        int idx;
        while ((idx = scoreBoard.locationOfScoreSequence(recipeCountString)) == -1) {
            recipeCreator.createNewRecipesAndAddThemToScoreBoard(scoreBoard, recipeCount);
        }
        return idx;
    }

}
