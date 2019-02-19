package be.tomjo.advent.day17;

import be.tomjo.advent.math.V2;
import be.tomjo.advent.search.SimpleSearchProblem;
import lombok.Value;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static be.tomjo.advent.util.GridUtil.initCharGrid;
import static java.util.stream.IntStream.rangeClosed;

public class GroundMap {

    private static final int PADDING = 10;
    private static final char CLAY = '#';
    private static final char SAND = '.';
    private static final char WATER_SOURCE = '+';
    private static final char WET = '|';
    private static final char WATER = '~';

    private final char[][] grid;
    private final int minY;
    private final int maxY;

    public GroundMap(List<V2> clayVeins) {
        int maxX = clayVeins.stream().mapToInt(V2::getXAsInt).max().orElseThrow(IllegalStateException::new);
        maxY = clayVeins.stream().mapToInt(V2::getYAsInt).max().orElseThrow(IllegalStateException::new);
        minY = clayVeins.stream().mapToInt(V2::getYAsInt).min().orElseThrow(IllegalStateException::new);
        grid = initCharGrid(maxX + PADDING, maxY + 1, SAND);
        for (V2 clayVein : clayVeins) {
            grid[clayVein.getXAsInt()][clayVein.getYAsInt()] = CLAY;
        }
    }

    private boolean containsClay(V2 location) {
        return grid[location.getXAsInt()][location.getYAsInt()] == CLAY;
    }

    private boolean isOnlyWet(V2 location) {
        return grid[location.getXAsInt()][location.getYAsInt()] == WET;
    }

    public void flowWaterFrom(V2 source) {
        grid[source.getXAsInt()][source.getYAsInt()] = WATER_SOURCE;
        Deque<V2> open = new ArrayDeque<>();
        open.add(source);
        while (!open.isEmpty()) {
            V2 current = open.poll();
            if (!containsClay(current.withOffsetY(1))) {
                grid[current.getXAsInt()][current.getYAsInt() + 1] = WET;
                if (current.getYAsInt() + 2 < grid[0].length) {
                    open.addFirst(current.withOffsetY(1));
                }
            } else {
                boolean filledLeftAndRight;
                int offsetY = 0;
                do {
                    Bounds xBounds = getBoundsBelowLocationContaining(current.withOffsetY(offsetY), WATER);
                    if (containsClay(new V2(xBounds.getMin() - 1, current.getYAsInt() + offsetY))
                            && containsClay(new V2(xBounds.getMax() + 1, current.getYAsInt() + offsetY))
                            && allInBoundsHaveType(xBounds, current.getYAsInt() + offsetY + 1, WATER)) {
                        filledLeftAndRight = fillLayer(current.withOffsetY(offsetY), WATER, open);
                    } else {
                        if (isOnlyWet(current.withOffsetY(offsetY + 1))) {
                            Bounds bounds = getBoundsBelowLocationContaining(current.withOffsetY(offsetY), WET);
                            if (allInBoundsHaveType(bounds, current.getYAsInt() + offsetY + 1, WET)) {
                                for (int x = bounds.getMin(); x <= bounds.getMax(); x++) {
                                    grid[x][current.getYAsInt() + offsetY + 1] = WATER;
                                }
                            }
                        }
                        filledLeftAndRight = fillLayer(current.withOffsetY(offsetY), WET, open);
                    }
                    offsetY--;
                } while (filledLeftAndRight);
            }
        }
    }

    private boolean fillLayer(V2 node, char type, Deque<V2> stack) {
        grid[node.getXAsInt()][node.getYAsInt()] = type;
        boolean left = fillLeft(node, type, stack);
        boolean right = fillRight(node, type, stack);
        return left && right;
    }

    private boolean fillLeft(V2 current, char c, Deque<V2> stack) {
        if (current.getXAsInt() + 1 >= grid.length || current.getXAsInt() - 1 < 0) {
            return true;
        }
        if (containsClay(current.withOffsetX(-1))) {
            return true;
        }
        if (grid[current.getXAsInt()][current.getYAsInt() + 1] != SAND) {
            grid[current.getXAsInt() - 1][current.getYAsInt()] = c;
            return fillLeft(current.withOffsetX(-1), c, stack);
        }
        if (containsClay(current.withOffsetX(1).withOffsetY(1))) {
            grid[current.getXAsInt()][current.getYAsInt()] = c;
            if (current.getYAsInt() + 1 < grid[0].length) {
                stack.addFirst(current);
            }
            return false;
        }
        grid[current.getXAsInt()][current.getYAsInt()] = SAND;
        return false;
    }

    private boolean fillRight(V2 current, char c, Deque<V2> stack) {
        if (current.getXAsInt() + 1 >= grid.length || current.getXAsInt() - 1 < 0) {
            return true;
        }
        if (containsClay(current.withOffsetX(1))) {
            return true;
        }
        if (grid[current.getXAsInt()][current.getYAsInt() + 1] != SAND) {
            grid[current.getXAsInt() + 1][current.getYAsInt()] = c;
            return fillRight(current.withOffsetX(1), c, stack);
        }
        if (containsClay(current.withOffsetX(-1).withOffsetY(1))) {
            grid[current.getXAsInt()][current.getYAsInt()] = c;
            grid[current.getXAsInt() - 1][current.getYAsInt()] = c;
            if (current.getYAsInt() + 1 < grid[0].length) {
                stack.addFirst(current);
            }
            return false;
        }
        grid[current.getXAsInt()][current.getYAsInt()] = SAND;
        return false;
    }

    private boolean allInBoundsHaveType(Bounds xBounds, int y, char c2) {
        return rangeClosed(xBounds.getMin(), xBounds.getMax())
                .map(x -> grid[x][y])
                .allMatch(c -> c == c2);
    }

    private Bounds getBoundsBelowLocationContaining(V2 location, char c) {
        int minX = location.getXAsInt();
        int maxX = location.getXAsInt();
        for (int x = 1; ; x++) {
            if (grid[location.getXAsInt() - x][location.getYAsInt() + 1] == c) {
                minX = location.getXAsInt() - x;
            } else {
                break;
            }
        }
        for (int x = 1; ; x++) {
            if (grid[location.getXAsInt() + x][location.getYAsInt() + 1] == c) {
                maxX = location.getXAsInt() + x;
            } else {
                break;
            }
        }
        return new Bounds(minX, maxX);
    }

    public int countWetRegions() {
        int count = 0;
        for (int y = minY; y <= maxY; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == WET || grid[x][y] == WATER || grid[x][y] == WATER_SOURCE) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countWaterRegions() {
        int count = 0;
        for (int y = minY; y <= maxY; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == WATER) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                if (grid[x][y] == CLAY) {
                    if (x < minX) {
                        minX = x;
                    }
                }
                if (x > maxX) {
                    maxX = x;
                }
            }
        }
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = minX - 1; x < maxX + 1; x++) {
                sb.append(grid[x][y]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Value
    static class Bounds {
        private int min, max;
    }
}
