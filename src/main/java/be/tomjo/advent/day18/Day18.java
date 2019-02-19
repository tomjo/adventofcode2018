package be.tomjo.advent.day18;

import be.tomjo.advent.Solution;
import be.tomjo.advent.util.GridUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static be.tomjo.advent.util.GridUtil.charGridToString;
import static be.tomjo.advent.util.StringUtils.lines;

public class Day18 implements Solution<Long, Long, char[][]> {

    public static void main(String[] args) {
        new Day18().run();
    }

    @Override
    public char[][] parse(String input) {
        List<String> rows = lines(input);
        int rowLength = rows.get(0).length();
        char[][] grid = new char[rowLength][rows.size()];
        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rowLength; x++) {
                grid[x][y] = rows.get(y).charAt(x);
            }
        }
        return grid;
    }

    private char[][] tick(char[][] grid) {
        char[][] result = GridUtil.copyGrid(grid);
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == '.' && adjacent(grid, x, y, '|') >= 3) {
                    result[x][y] = '|';
                } else if (grid[x][y] == '|' && adjacent(grid, x, y, '#') >= 3) {
                    result[x][y] = '#';
                } else if (grid[x][y] == '#' && (adjacent(grid, x, y, '#') <= 0 || adjacent(grid, x, y, '|') <= 0)) {
                    result[x][y] = '.';
                }
            }
        }
        return result;
    }

    private int adjacent(char[][] grid, int x, int y, char type) {
        return (int) adjacent(grid, x, y).stream()
                .filter(c -> c == type)
                .count();
    }

    private List<Character> adjacent(char[][] grid, int x, int y) {
        List<Character> adjacent = new ArrayList<>();
        if (x > 0) {
            adjacent.add(grid[x - 1][y]);
        }
        if (y > 0) {
            adjacent.add(grid[x][y - 1]);
        }
        if (x < grid.length - 1) {
            adjacent.add(grid[x + 1][y]);
        }
        if (y < grid[0].length - 1) {
            adjacent.add(grid[x][y + 1]);
        }
        if (x > 0 && y > 0) {
            adjacent.add(grid[x - 1][y - 1]);
        }
        if (x > 0 && y < grid[0].length - 1) {
            adjacent.add(grid[x - 1][y + 1]);
        }
        if (x < grid.length - 1 && y > 0) {
            adjacent.add(grid[x + 1][y - 1]);
        }
        if (x < grid.length - 1 && y < grid[0].length - 1) {
            adjacent.add(grid[x + 1][y + 1]);
        }
        return adjacent;
    }

    @Override
    public Long part1(char[][] input) {
        char[][] result = input;
        for (int i = 0; i < 10; i++) {
            result = tick(result);
        }
        return score(result);
    }

    @Override
    public Long part2(char[][] input) {
        Map<String, Long> gridStringIterationMapping = new HashMap<>();
        Map<Long, char[][]> iterationGridMapping = new HashMap<>();
        String charGridString = null;
        int minute = 0;
        int minutes = 1000000000;
        char[][] result = input;
        while (minute < minutes) {
            charGridString = charGridToString(result);
            if (gridStringIterationMapping.containsKey(charGridString)) {
                break;
            }
            gridStringIterationMapping.put(charGridString, (long) minute);
            iterationGridMapping.put((long) minute, result);
            minute++;
            result = tick(result);
        }
        long previousMinuteWithGrid = gridStringIterationMapping.get(charGridString);
        char[][] gridAfterMinutes = iterationGridMapping.get(previousMinuteWithGrid + (minutes - minute) % (minute - previousMinuteWithGrid));
        return score(gridAfterMinutes);
    }

    private long score(char[][] result) {
        long trees = 0;
        long lumberYards = 0;
        for (int y = 0; y < result[0].length; y++) {
            for (int x = 0; x < result.length; x++) {
                if (result[x][y] == '|') {
                    trees++;
                } else if (result[x][y] == '#') {
                    lumberYards++;
                }
            }
        }
        return trees * lumberYards;
    }
}
