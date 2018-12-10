package be.tomjo.advent.day6;

import be.tomjo.advent.Solution;
import be.tomjo.advent.math.V2;
import lombok.Value;

import java.util.*;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Day6 implements Solution<Integer, Integer, Day6.Day6Input> {

    public static int DISTANCE_LIMIT = 10000;

    public static void main(String[] args) {
        new Day6().run();
    }

    @Override
    public Day6Input parse(String input) {
        List<V2> coords = inputToCoords(input);
        V2 coordBounds = calculateCoordBoundaries(coords, DISTANCE_LIMIT);
        return new Day6Input(coords, coordBounds);
    }

    @Override
    public Integer part1(Day6Input input) {
        Map<Integer, Integer> coordAreaCount = createCoordAreaCountMap(input.getCoords(), input.getBounds());
        return getHighestCoordAreaCount(coordAreaCount);
    }

    @Override
    public Integer part2(Day6Input input) {
        return getTotalRegionSizeOfRegionContainingAllLocationsWithATotalDistanceLessThanDistanceLimit(input.getCoords(), input.getBounds(), DISTANCE_LIMIT);
    }

    private static int getTotalRegionSizeOfRegionContainingAllLocationsWithATotalDistanceLessThanDistanceLimit(List<V2> coords, V2 coordBounds, int distanceLimit) {
        return range(0, coordBounds.getYAsInt())
                .map(y -> (int) range(0, coordBounds.getXAsInt())
                        .mapToObj(x -> new V2(x, y))
                        .mapToInt(coord -> getTotalDistanceFromCoordsToCoord(coords, coord))
                        .filter(sum -> sum < distanceLimit)
                        .count())
                .sum();
    }

    private static int getTotalDistanceFromCoordsToCoord(List<V2> coords, V2 coord) {
        return coords.stream()
                .mapToInt(otherCoord -> manhattanDistanceToPoint(otherCoord, coord))
                .sum();
    }

    private static V2 calculateCoordBoundaries(List<V2> coords, int distanceLimit) {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (V2 coord : coords) {
            if (coord.getX() > maxX) {
                maxX = coord.getXAsInt();
            }
            if (coord.getY() > maxY) {
                maxY = coord.getYAsInt();
            }
        }
        return new V2(maxX + (distanceLimit / coords.size()) + 1, maxY + (DISTANCE_LIMIT / coords.size()) + 1);
    }

    private static int getHighestCoordAreaCount(Map<Integer, Integer> coordAreaCount) {
        return coordAreaCount.values()
                .stream()
                .max(comparingInt(i -> i))
                .orElseThrow(IllegalStateException::new);
    }

    private static Map<Integer, Integer> createCoordAreaCountMap(List<V2> coords, V2 coordBounds) {
        Map<Integer, Integer> coordAreaCount = new HashMap<>();
        for (int y = 0; y < coordBounds.getY(); y++) {
            for (int x = 0; x < coordBounds.getX(); x++) {
                V2 coord = new V2(x, y);
                int coordIndex = getIndexClosestCoordToPoint(coords, coord);
                coordAreaCount.merge(coordIndex, 1, Day6::mergeAreaCount);
                if (coordAreaExtendsInfinitelyForCoord(coord, coordBounds)) {
                    coordAreaCount.put(coordIndex, -1);
                }
            }
        }
        return coordAreaCount;
    }

    private static int mergeAreaCount(int a, int b) {
        if (a == -1) {
            return -1;
        }
        if (b == -1) {
            return -1;
        }
        return a + b;
    }

    private static boolean coordAreaExtendsInfinitelyForCoord(V2 coord, V2 boundaries) {
        return coord.getY() == 0 || coord.getY() == boundaries.getY() - 1 || coord.getX() == 0 || coord.getX() == boundaries.getX() - 1;
    }

    private static int getIndexClosestCoordToPoint(List<V2> coords, V2 coord) {
        int currentMinimalDistance = Integer.MAX_VALUE;
        int indexOfCoordWithMinimalDistance = 0;
        for (int i = 0; i < coords.size(); i++) {
            int distance = manhattanDistanceToPoint(coord, coords.get(i));
            if (distance < currentMinimalDistance) {
                currentMinimalDistance = distance;
                indexOfCoordWithMinimalDistance = i;
            } else if (distance == currentMinimalDistance) {
                indexOfCoordWithMinimalDistance = -1;
            }
        }
        return indexOfCoordWithMinimalDistance;
    }

    private static int manhattanDistanceToPoint(V2 p, V2 q) {
        return (Math.abs(p.getXAsInt() - q.getXAsInt()) + Math.abs(p.getYAsInt() - q.getYAsInt()));
    }

    private static List<V2> inputToCoords(String input) {
        return lines(input).stream()
                .map(s -> s.split(", "))
                .map(p -> new V2(parseInt(p[0]), parseInt(p[1])))
                .collect(toList());
    }

    @Value
    static class Day6Input {
        List<V2> coords;
        V2 bounds;
    }

}