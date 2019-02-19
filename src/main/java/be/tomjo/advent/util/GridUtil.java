package be.tomjo.advent.util;

import lombok.experimental.UtilityClass;

import java.util.function.BiFunction;

@UtilityClass
public class GridUtil {

    public static String charGridToString(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static char[][] initCharGrid(int width, int height, char initChar) {
        char[][] grid = new char[width][height];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = initChar;
            }
        }
        return grid;
    }

    public static char[][] initCharGrid(int width, int height, BiFunction<Integer, Integer, Character> charFunction) {
        char[][] grid = new char[width][height];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = charFunction.apply(i, j);
            }
        }
        return grid;
    }


    public static char[][] copyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                copy[i][j] = grid[i][j];
            }
        }
        return copy;
    }


}
