package be.tomjo.advent.day1;

import be.tomjo.advent.Solution;

import java.util.Set;
import java.util.stream.IntStream;

import static be.tomjo.advent.util.StringUtils.lines;
import static com.google.common.collect.Sets.newHashSet;

public class Day1 implements Solution<Integer, Integer, int[]> {

    public static void main(String[] args) {
        new Day1().run();
    }

    @Override
    public int[] parse(String input) {
        return lines(input).stream().mapToInt(Integer::parseInt).toArray();
    }

    @Override
    public Integer part1(int[] numbers) {
        return IntStream.of(numbers).sum();
    }

    @Override
    public Integer part2(int[] numbers) {
        Set<Integer> visited = newHashSet(0);
        int frequency = 0;
        for (; ; ) {
            for (int number : numbers) {
                frequency += number;
                if (!visited.add(frequency)) {
                    return frequency;
                }
            }
        }
    }
}
