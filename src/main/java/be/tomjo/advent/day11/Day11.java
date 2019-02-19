package be.tomjo.advent.day11;

import be.tomjo.advent.Solution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Integer.parseInt;

public class Day11 implements Solution<String, String, int[][]> {

    public static void main(String[] args) {
        new Day11().run();
    }

    @Override
    public int[][] parse(String input) {
        int gridSerialNumber = parseInt(lines(input).get(0));
        return createPartialSumsGrid(gridSerialNumber);
    }

    private int[][] createPartialSumsGrid(int gridSerialNumber) {
        int[][] grid = new int[301][301];
        for (int x = 1; x <= 300; x++) {
            for (int y = 1; y <= 300; y++) {
                int power = calcPower(x, y, gridSerialNumber);
                grid[x][y] = power + grid[x - 1][y] + grid[x][y - 1] - grid[x - 1][y - 1];
            }
        }
        return grid;
    }


    @Override
    public String part1(int[][] partialSums) {
        int highestPowerX = 0;
        int highestPowerY = 0;
        int highestPower = Integer.MIN_VALUE;
        int gridSize = 3;
        for (int x = gridSize; x <= 300; x++) {
            for (int y = gridSize; y <= 300; y++) {
                int power = partialSums[x][y] - partialSums[x - gridSize][y] - partialSums[x][y - gridSize] + partialSums[x - gridSize][y - gridSize];
                if (power > highestPower) {
                    highestPower = power;
                    highestPowerX = x;
                    highestPowerY = y;
                }
            }
        }
        return (highestPowerX - 3 + 1) + "," + (highestPowerY - 3 + 1);
    }

    @Override
    public String part2(int[][] partialSums) {
        int highestPowerX = 0;
        int highestPowerY = 0;
        int highestPower = Integer.MIN_VALUE;
        int gridSizeResultingInHighestPower = 1;
        for (int gridSize = 1; gridSize <= 300; gridSize++) {
            for (int x = gridSize; x <= 300; x++) {
                for (int y = gridSize; y <= 300; y++) {
                    int power = partialSums[x][y] - partialSums[x - gridSize][y] - partialSums[x][y - gridSize] + partialSums[x - gridSize][y - gridSize];
                    if (power > highestPower) {
                        highestPower = power;
                        highestPowerX = x;
                        highestPowerY = y;
                        gridSizeResultingInHighestPower = gridSize;
                    }
                }
            }
        }
        return (highestPowerX - gridSizeResultingInHighestPower + 1) + "," + (highestPowerY - gridSizeResultingInHighestPower + 1) + "," + gridSizeResultingInHighestPower;
    }

    private int calcPower(int x, int y, int gridSerialNumber) {
        int rackId = x + 10;
        return ((rackId * y) + gridSerialNumber) * rackId / 100 % 10 - 5;
    }

    @Data
    @AllArgsConstructor
    static class PowerGrid {
        private int power;
        private int x;
        private int y;
        private int gridSize;
    }
}
