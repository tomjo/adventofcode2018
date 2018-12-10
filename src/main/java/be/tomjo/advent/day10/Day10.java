package be.tomjo.advent.day10;

import be.tomjo.advent.Solution;
import be.tomjo.advent.math.Rect;
import be.tomjo.advent.math.V2;
import be.tomjo.advent.util.StringUtils;
import lombok.Value;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.tomjo.advent.util.GridUtil.charGridToString;
import static be.tomjo.advent.util.GridUtil.initCharGrid;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

public class Day10 implements Solution<String, Integer, Day10.LightsAndTheirIterationCount> {

    private static final Pattern INPUT_REGEX = Pattern.compile("^position=<(.+), (.+)> velocity=<(.+), (.+)>$");

    public static void main(String[] args) {
        new Day10().run();
    }

    @Override
    public LightsAndTheirIterationCount parse(String input) {
        List<Light> lights = StringUtils.lines(input).stream()
                .map(INPUT_REGEX::matcher)
                .filter(Matcher::find)
                .map(m -> new Light(new V2(parseInt(m.group(1).trim()), parseInt(m.group(2).trim())),
                        new V2(parseInt(m.group(3).trim()), parseInt(m.group(4).trim()))))
                .collect(toList());
        int iterationCount = iterateLightsUntilBoundingBoxStartsDivergingAgain(lights);
        return new LightsAndTheirIterationCount(calculateLightPosition(lights, iterationCount), iterationCount);
    }

    @Override
    public String part1(LightsAndTheirIterationCount lightsAndTheirIterationCount) {
        Collection<Light> lightsSpellingMessage = lightsAndTheirIterationCount.getLights();
        Rect boundingBox = calculateBoundingBox(lightsSpellingMessage);
        String rendered = render(lightsSpellingMessage, boundingBox);
        return "\n" + rendered.substring(0, rendered.length() - 1);
    }

    @Override
    public Integer part2(LightsAndTheirIterationCount lightsAndTheirIterationCount) {
        return lightsAndTheirIterationCount.getIterationCount();
    }

    private Collection<Light> calculateLightPosition(Collection<Light> lights, int iterations) {
        return lights.stream()
                .map(light -> light.iterate(iterations))
                .collect(toList());
    }

    private Rect calculateBoundingBox(Collection<Light> lights) {
        long minX = Integer.MAX_VALUE;
        long maxX = Integer.MIN_VALUE;
        long minY = Integer.MAX_VALUE;
        long maxY = Integer.MIN_VALUE;
        for (Light light : lights) {
            long gridX = light.getPosition().getX();
            long gridY = light.getPosition().getY();
            if (gridX < minX) {
                minX = gridX;
            }
            if (gridY < minY) {
                minY = gridY;
            }
            if (gridX > maxX) {
                maxX = gridX;
            }
            if (gridY > maxY) {
                maxY = gridY;
            }
        }
        return new Rect(new V2(minX, minY), new V2(maxX, maxY));
    }

    private String render(Collection<Light> lights, Rect boundingBox) {
        int minX = (int) boundingBox.getMin().getX();
        int minY = (int) boundingBox.getMin().getY();
        int width = (int) (boundingBox.getMax().getX() - minX + 1);
        int height = (int) (boundingBox.getMax().getY() - minY + 1);
        char[][] grid = initCharGrid(width, height, '.');
        fillCharGridWithLightsIfTheyFitInGrid(lights, minX, minY, grid);
        return charGridToString(grid);
    }

    private void fillCharGridWithLightsIfTheyFitInGrid(Collection<Light> lights, int minX, int minY, char[][] grid) {
        lights.stream()
                .filter(light -> lightFitsGrid(grid, light.getPosition().getX() - minX, light.getPosition().getY() - minY))
                .forEach(light -> grid[(int) (light.getPosition().getX() - minX)][(int) (light.getPosition().getY() - minY)] = '#');
    }

    private boolean lightFitsGrid(char[][] grid, long scaledLightX, long scaledLightY) {
        return scaledLightX >= 0 && scaledLightX < grid.length && scaledLightY >= 0 && scaledLightY < grid[0].length;
    }

    private int iterateLightsUntilBoundingBoxStartsDivergingAgain(Collection<Light> lights) {
        int iterationCount = 0;
        Rect previousBoundingBox;
        do {
            previousBoundingBox = calculateBoundingBox(lights);
            lights = iterate(lights);
            iterationCount++;
        } while (calculateBoundingBox(lights).smallerThan(previousBoundingBox));
        return iterationCount - 1;
    }

    private Collection<Light> iterate(Collection<Light> input) {
        return input.stream()
                .map(light -> light.iterate(1))
                .collect(toList());
    }

    @Value
    static class LightsAndTheirIterationCount {
        private final Collection<Light> lights;
        private final int iterationCount;
    }
}
