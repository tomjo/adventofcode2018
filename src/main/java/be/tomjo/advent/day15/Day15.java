package be.tomjo.advent.day15;

import be.tomjo.advent.Solution;

import static java.util.Arrays.asList;

public class Day15 implements Solution<Integer, Integer, GameMap> {

    public static void main(String[] args) {
        new Day15().run();
    }

    @Override
    public GameMap parse(String input) {
        return new GameMapImpl(input);
    }

    @Override
    public Integer part1(GameMap input) {
        GameMap map = input.copy();
        map.initialize(3);
        Game game = new Game(map);
        game.play();
        return game.getScore();
    }

    //TODO binary search? but have to make it faster...
    // -> binary search not correct for every input (If elves win if their hit point is n, then they win for any hit points greater than n as well.
    // => not correct)
    // (with higher attackpower, an elf might kill something sooner, join a different fight and die)
    //correct result: 23
    //48722
    @Override
    public Integer part2(GameMap input) {
        GameMap map = input.copy();
        map.initialize(23);
        int originalElfSize = map.getUnitsOfType(asList(UnitType.ELF)).size();
        Game game = new Game(map);
        game.play();
        int elfSize = map.getUnitsOfType(asList(UnitType.ELF)).size();
        System.out.println(originalElfSize == elfSize);
        return game.getScore();
    }
}
