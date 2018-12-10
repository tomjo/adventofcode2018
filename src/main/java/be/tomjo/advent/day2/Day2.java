package be.tomjo.advent.day2;

import be.tomjo.advent.Solution;
import be.tomjo.advent.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day2 implements Solution<Integer, String, List<String>> {

    public static void main(String[] args) {
        new Day2().run();
    }

    @Override
    public List<String> parse(String input) {
        return StringUtils.lines(input);
    }

    @Override
    public Integer part1(List<String> boxIds) {
        int twos = 0;
        int threes = 0;
        for (String candidate : boxIds) {
            Map<Character, Long> frequencyMap = createCharacterFrequencyMap(candidate);
            twos += frequencyMap.containsValue(2L) ? 1 : 0;
            threes += frequencyMap.containsValue(3L) ? 1 : 0;
        }
        return twos * threes;
    }

    private static Map<Character, Long> createCharacterFrequencyMap(String candidate) {
        return candidate.chars()
                .mapToObj(c -> (char) c)
                .collect(groupingBy(Function.identity(), counting()));
    }

    @Override
    public String part2(List<String> boxIds) {
        for (int i = 0; i < boxIds.size(); i++) {
            for (int j = i + 1; j < boxIds.size(); j++) {
                if (differByOneSubstitution(boxIds.get(i), boxIds.get(j))) {
                    return getCommonLetters(boxIds.get(i), boxIds.get(j));
                }
            }
        }
        throw new IllegalStateException();
    }

    private static boolean differByOneSubstitution(String x, String y) {
        int differenceCount = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) != y.charAt(i)) {
                differenceCount++;
            }
            if (differenceCount > 1) {
                return false;
            }
        }
        return true;
    }

    private static String getCommonLetters(String x, String y) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == y.charAt(i)) {
                result.append(x.charAt(i));
            }
        }
        return result.toString();
    }

}
