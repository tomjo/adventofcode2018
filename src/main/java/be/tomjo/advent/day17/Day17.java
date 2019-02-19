package be.tomjo.advent.day17;

import be.tomjo.advent.Solution;
import be.tomjo.advent.math.V2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

public class Day17 implements Solution<Integer, Integer, GroundMap> {

    private static V2 WATER_SOURCE_LOCATION = new V2(500, 0);

    public static void main(String[] args) {
        new Day17().run();
    }

    @Override
    public Integer part1(GroundMap groundMap) {
        return groundMap.countWetRegions();
    }

    @Override
    public Integer part2(GroundMap groundMap) {
        return groundMap.countWaterRegions();
    }

    @Override
    public GroundMap parse(String input) {
        List<V2> clayVeinLocations = lines(input).stream()
                .map(this::parseClayVeinLocations).flatMap(Collection::stream)
                .collect(toList());
        GroundMap groundMap = new GroundMap(clayVeinLocations);
        groundMap.flowWaterFrom(WATER_SOURCE_LOCATION);
        return groundMap;
    }

    private Collection<V2> parseClayVeinLocations(String line) {
        String[] parts = line.split(", ");
        int[] xs;
        int[] ys;
        if (parts[0].startsWith("x=")) {
            xs = parseCoords(parts[0].substring(2));
            ys = parseCoords(parts[1].substring(2));
        } else {
            xs = parseCoords(parts[1].substring(2));
            ys = parseCoords(parts[0].substring(2));
        }
        List<V2> clayVeinLocations = new ArrayList<>();
        for (int x = 0; x < xs.length; x++) {
            for (int y = 0; y < ys.length; y++) {
                clayVeinLocations.add(new V2(xs[x], ys[y]));
            }
        }
        return clayVeinLocations;
    }

    private int[] parseCoords(String coordPart) {
        String[] xParts = coordPart.split("\\.\\.");
        if (xParts.length == 1) {
            return new int[]{parseInt(xParts[0])};
        }
        return rangeClosed(parseInt(xParts[0]), parseInt(xParts[1])).toArray();
    }
}
