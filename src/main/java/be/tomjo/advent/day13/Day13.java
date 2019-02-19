package be.tomjo.advent.day13;

import be.tomjo.advent.Solution;
import be.tomjo.advent.math.V2;

import java.util.ArrayList;
import java.util.List;

import static be.tomjo.advent.util.GridUtil.copyGrid;
import static be.tomjo.advent.util.StringUtils.lines;
import static java.util.Arrays.asList;

public class Day13 implements Solution<String, String,TrackSystem> {

    public static void main(String[] args) {
        new Day13().run();
    }

    @Override
    public String part1(TrackSystem input) {
        Crash crash = input.moveCartsUntilCrash();
        return crash.getMineCart1().getLocation().getXAsInt()+","+crash.getMineCart1().getLocation().getYAsInt();
    }

    @Override
    public String part2(TrackSystem input) {
        MineCart remainingCart = input.moveCartsUntilOneMineCartRemains();
        return remainingCart.getLocation().getXAsInt()+","+remainingCart.getLocation().getYAsInt();
    }

    @Override
    public TrackSystem parse(String input) {
        List<String> lines = lines(input);
        char[][] grid = new char[lines.get(0).length()][lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).toCharArray().length; j++) {
                grid[j][i] = lines.get(i).toCharArray()[j];
            }
        }
        char[][] gridWithoutCarts = getGridWithoutCarts(grid);
        List<MineCart> mineCarts = getMineCartLocations(grid);
        return new TrackSystem(gridWithoutCarts, mineCarts);
    }

    private List<MineCart> getMineCartLocations(char[][] grid) {
        List<MineCart> locations = new ArrayList<>();
        int id = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (asList('<', '>', '^', 'v').contains(grid[x][y])) {
                    locations.add(new MineCart(id++, new V2(x, y), Direction.of(grid[x][y]), TurnState.LEFT));
                }
            }
        }
        return locations;
    }

    private char[][] getGridWithoutCarts(char[][] grid) {
        char[][] gridWithoutCarts = copyGrid(grid);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '<' || grid[i][j] == '>') {
                    gridWithoutCarts[i][j] = '-';
                } else if (grid[i][j] == '^' || grid[i][j] == 'v') {
                    gridWithoutCarts[i][j] = '|';
                }
            }
        }
        return gridWithoutCarts;
    }
}
