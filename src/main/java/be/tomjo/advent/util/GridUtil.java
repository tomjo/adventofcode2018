package be.tomjo.advent.util;

import lombok.experimental.UtilityClass;

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

}
