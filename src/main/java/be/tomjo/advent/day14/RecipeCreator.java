package be.tomjo.advent.day14;

import static be.tomjo.advent.util.ArrayUtils.copy;
import static be.tomjo.advent.util.ArrayUtils.sum;
import static be.tomjo.advent.util.MathUtil.digits;

public class RecipeCreator {

    private int[] elfPositions;
    private int[] currentRecipes;

    public RecipeCreator(int[] elfPositions, int[] currentRecipes) {
        this.elfPositions = copy(elfPositions);
        this.currentRecipes = copy(currentRecipes);
    }

    public void createNewRecipesAndAddThemToScoreBoard(ScoreBoard scoreBoard, int amount) {
        for (int i = 0; i < amount; i++) {
            scoreBoard.add(combineRecipes());
            chooseNewRecipesToCombine(scoreBoard);
        }
    }

    private int[] combineRecipes() {
        int recipeSum = sum(currentRecipes);
        int[] newRecipes = new int[digits(recipeSum)];
        int pos = newRecipes.length - 1;
        while (recipeSum > 0) {
            newRecipes[pos--] = recipeSum % 10;
            recipeSum /= 10;
        }
        return newRecipes;
    }

    private void chooseNewRecipesToCombine(ScoreBoard scoreBoard) {
        for (int i = 0; i < currentRecipes.length; i++) {
            elfPositions[i] = (elfPositions[i] + 1 + currentRecipes[i]) % scoreBoard.getScoreCount();
            currentRecipes[i] = scoreBoard.getScoreAt(elfPositions[i]);
        }
    }

}
