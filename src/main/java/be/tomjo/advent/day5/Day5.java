package be.tomjo.advent.day5;

import be.tomjo.advent.Solution;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
import static java.util.stream.Collectors.toSet;

public class Day5 implements Solution<Integer, Integer, String> {

    public static void main(String[] args) {
        new Day5().run();
    }

    @Override
    public String parse(String input) {
        return extractPolymer(input);
    }

    @Override
    public Integer part1(String polymer) {
        return getPolymerLengthAfterReactions(polymer);
    }

    @Override
    public Integer part2(String polymer) {
        return getPolymerUnitTypesUsedInPolymer(polymer).stream()
                .map(polymerUnitType -> stripUnitFromPolymer(polymer, polymerUnitType))
                .mapToInt(Day5::getPolymerLengthAfterReactions)
                .min()
                .orElseThrow(IllegalStateException::new);
    }

    private static int getPolymerLengthAfterReactions(String polymer) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char polymerUnit : polymer.toCharArray()) {
            if (!stack.isEmpty() && polymerUnitsReact(polymerUnit, stack.peek())) {
                stack.pop();
            } else {
                stack.push(polymerUnit);
            }
        }
        return stack.size();
    }

    private static Set<Character> getPolymerUnitTypesUsedInPolymer(String polymer) {
        return polymer.chars()
                .mapToObj(c -> (char) c)
                .map(Character::toLowerCase)
                .collect(toSet());
    }

    private static String stripUnitFromPolymer(String polymer, char polymerUnitType) {
        return polymer.replaceAll("["+polymerUnitType+ toUpperCase(polymerUnitType)+"]", "");
    }

    private static boolean polymerUnitsReact(char u, char v) {
        return toLowerCase(u) == toLowerCase(v) && u != v;
    }

    private static String extractPolymer(String input) {
        return lines(input).get(0);
    }
}
