package be.tomjo.advent.day12;

import be.tomjo.advent.Solution;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static be.tomjo.advent.util.StringUtils.lines;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

public class Day12 implements Solution<Long, Long, Day12.Day12Input> {


    private static final int PATTERN_DETECTION_THRESHOLD = 3;

    public static void main(String[] args) {
        new Day12().run();
    }

    @Override
    public Long part1(Day12Input input) {
        return calculatePlantScoreAfterGenerations(input, 20);
    }

    @Override
    public Long part2(Day12Input input) {
        long GENERATIONS = 50000000000L;
        int offset = 0;
        Map<String, List<GenerationScore>> encounteredPatterns = new HashMap<>();
        String state = input.getInitialState();

        for (long generation = 0; generation < GENERATIONS; generation++) {
            if (state.substring(0, 4).contains("#")) {
                offset += 4;
            }
            state = calculateNextGeneration(addPaddingIfRequired(state), input.getRules());

            long potScore = calcScore(state, offset);
            String potString = state.substring(state.indexOf('#'), state.lastIndexOf('#') + 1);
            addPattern(encounteredPatterns, potString, potScore, generation+1);

            List<GenerationScore> generationScores = encounteredPatterns.get(potString);
            if (generationScores.size() >= PATTERN_DETECTION_THRESHOLD
                    && verifyPatternScores(generationScores)) {
                return extraPolateScore(input, generationScores, GENERATIONS);
            }
        }
        return calcScore(state, offset);
    }

    private void addPattern(Map<String, List<GenerationScore>> patterns, String potString, long score, long generation){
        patterns.merge(potString, newArrayList(new GenerationScore(generation, score)), (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        });
    }

    private long extraPolateScore(Day12Input input, List<GenerationScore> generationScores, long generations) {
        long generationA = generationScores.get(0).getGeneration();
        long generationB = generationScores.get(1).getGeneration();
        long solutionA = calculatePlantScoreAfterGenerations(input, generationA);
        long solutionB = calculatePlantScoreAfterGenerations(input, generationB);
        return solutionA + (solutionB - solutionA) * (generations - generationA);
    }

    private boolean verifyPatternScores(List<GenerationScore> generationScores){
        long[] scoreDifferences = new long[generationScores.size()-1];
        for (int i = 1; i < generationScores.size(); i++) {
            long generationDifference = generationScores.get(i).getGeneration() - generationScores.get(i-1).getGeneration();
            scoreDifferences[i-1] = (generationScores.get(i).getScore() - generationScores.get(i-1).getScore()) / generationDifference;
        }
        return stream(scoreDifferences)
                .distinct()
                .count() == 1;
    }

    private long calculatePlantScoreAfterGenerations(Day12Input input, long generations) {
        String state = input.getInitialState();
        int offset = 0;
        for (long i = 0; i < generations; i++) {
            if (state.substring(0, 4).contains("#")) {
                offset += 4;
            }
            state = calculateNextGeneration(addPaddingIfRequired(state), input.getRules());
        }
        return calcScore(state, offset);
    }

    private String addPaddingIfRequired(String state) {
        if (state.substring(0, 4).contains("#")) {
            state = "...." + state;
        }
        if (state.substring(state.length() - 4).contains("#")) {
            state += "....";
        }
        return state;
    }

    private String calculateNextGeneration(String initialState, Map<String, String> rules) {
        String state = initialState;
        for (int pos = 2; pos < initialState.length() - 2; pos++) {
            String slidingWindow = initialState.substring(pos - 2, pos + 3);
            state = state.substring(0, pos) + rules.getOrDefault(slidingWindow, ".") + state.substring(pos + 1);
        }
        return state;
    }

    private long calcScore(String state, int offset) {
        return range(0, state.length())
                .filter(i -> state.charAt(i) == '#')
                .mapToLong(i -> (i - offset))
                .sum();
    }

    @Override
    public Day12Input parse(String input) {
        List<String> lines = lines(input);
        String initialState = lines.get(0).substring("initial state: ".length());
        Map<String, String> rules = lines.stream()
                .map(s -> s.split(" "))
                .collect(toMap(this::parseRule, this::parseResult));
        return new Day12Input(initialState, rules);
    }

    private String parseResult(String[] r) {
        return r[2];
    }

    private String parseRule(String[] r) {
        return r[0];
    }

    @Value
    static class Day12Input {
        String initialState;
        Map<String, String> rules;
    }

    @Value
    private static class GenerationScore {
        long generation;
        long score;
    }
}