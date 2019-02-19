package be.tomjo.advent.day15.pathfinding;

import be.tomjo.advent.day15.GameMap;
import be.tomjo.advent.day15.LocationComparator;
import be.tomjo.advent.day15.Unit;
import be.tomjo.advent.math.V2;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DijkstraPathFinder {

    private final GameMap gameMap;
//    private final Set<V2> occupiedPoints;
    private final Graph<V2, DefaultEdge> graph;
    private final V2 startingPoint;

    public DijkstraPathFinder(GameMap gameMap, final V2 startingPoint) {
        this.gameMap = gameMap;
//        this.occupiedPoints = occupiedPoints;
        this.startingPoint = startingPoint;

        // Build the graph
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final Set<V2> visited = new HashSet<>();
        final Stack<V2> stack = new Stack<>();
        stack.push(startingPoint);
        while (!stack.isEmpty()) {
            final V2 p = stack.pop();
            if (visited.add(p)) {
                graph.addVertex(p);
                for (final V2 adjacent : getReachableAdjacentEdges(p)) {
                    graph.addVertex(adjacent);
                    graph.addEdge(p, adjacent);
                    stack.push(adjacent);
                }
            }
        }
    }

    /**
     * Gets the neighbors around a point
     */
    final Function<V2, Stream<V2>> neighbors = p -> Stream.of(
            new V2(p.getXAsInt() - 1, p.getYAsInt()),
            new V2(p.getXAsInt() + 1, p.getYAsInt()),
            new V2(p.getXAsInt(), p.getYAsInt() - 1),
            new V2(p.getXAsInt(), p.getYAsInt() + 1));

    /**
     * Calculates the best move to make to reach the given enemies
     */
    public Optional<V2> calculateMove(final Collection<V2> locations) {
        final DijkstraShortestPath dijkstra = new DijkstraShortestPath<>(graph);
        final Map<V2, ShortestPathAlgorithm.SingleSourcePaths<V2, DefaultEdge>> paths = new ConcurrentHashMap<>();

        // Get the points surrounding enemies
        return locations.stream()
                .filter(point -> paths.computeIfAbsent(startingPoint, s -> dijkstra.getPaths(startingPoint)).getPath(point) != null)
                // Collect the points into a distance map
                .collect(Collectors.groupingBy(point -> paths.get(startingPoint).getPath(point).getLength(), Collectors.toList())).entrySet().stream()
                // Get the points that have the shortest distance
                .min(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).orElse(Collections.emptyList()).stream()
                // Tie-break points by reading order
                .min(new LocationComparator())
                .map(destination ->
                        // Get the points surrounding the starting point
                        neighbors.apply(startingPoint)
                                // Remove any unreachable points
                                .filter(graph::containsVertex)
                                .filter(point -> paths.computeIfAbsent(destination, s -> dijkstra.getPaths(destination)).getPath(point) != null)
                                // Collect the points into a map of how fast they are
                                .collect(Collectors.groupingBy(point -> paths.get(destination).getPath(point).getLength(), Collectors.toList())).entrySet().stream()
                                // Get the points that get to our destination the fastest
                                .min(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue).orElse(Collections.emptyList()).stream()
                                // Tie-break points by reading order
                                .min(new LocationComparator()).orElseThrow(IllegalStateException::new));
    }

    /**
     * Get all reachable adjacent edges from the given point
     */
    List<V2> getReachableAdjacentEdges(final V2 point) {
        return new ArrayList<>(gameMap.getMoveableLocationsInRangeOfLocation(point));
//        final List<V2> adjacentPoints = new ArrayList<>(4);
//        // Test if the point to the left is reachable
//        if (point.getXAsInt() - 1 >= 0 && cave[point.getXAsInt() - 1][point.getYAsInt()] != '#') {
//            final V2 p = new V2(point.getXAsInt() - 1, point.getYAsInt());
//            if (!occupiedPoints.contains(p)) {
//                adjacentPoints.add(p);
//            }
//        }
//        // Test if the point to the right is reachable
//        if (point.getXAsInt() + 1 < cave.length && cave[point.getXAsInt() + 1][point.getYAsInt()] != '#') {
//            final V2 p = new V2(point.getXAsInt() + 1, point.getYAsInt());
//            if (!occupiedPoints.contains(p)) {
//                adjacentPoints.add(p);
//            }
//        }
//        // Test if the point above is reachable
//        if (point.getYAsInt() - 1 >= 0 && cave[point.getXAsInt()][point.getYAsInt() - 1] != '#') {
//            final V2 p = new V2(point.getXAsInt(), point.getYAsInt() - 1);
//            if (!occupiedPoints.contains(p)) {
//                adjacentPoints.add(p);
//            }
//        }
//        // Test if the point below is reachable
//        if (point.getYAsInt() + 1 < cave[0].length && cave[point.getXAsInt()][point.getYAsInt() + 1] != '#') {
//            final V2 p = new V2(point.getXAsInt(), point.getYAsInt() + 1);
//            if (!occupiedPoints.contains(p)) {
//                adjacentPoints.add(p);
//            }
//        }
//        return adjacentPoints;
    }


}
