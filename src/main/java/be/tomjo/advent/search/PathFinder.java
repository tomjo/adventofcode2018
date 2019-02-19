package be.tomjo.advent.search;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Collections.reverse;

@UtilityClass
public class PathFinder {

    public static <T, C, S> List<PathSearchStep<T, C>> findShortestPath(SearchProblem<T, C, S> problem, Supplier<Collection<PathSearchStep<T, C>>> nodeContainerSupplier) {
        Collection<PathSearchStep<T, C>> open = nodeContainerSupplier.get();
        Set<T> closed = new HashSet<>();
        Map<PathSearchStep<T, C>, PathSearchStep<T, C>> parentNodes = new HashMap<>();
        PathSearchStep<T, C> rootNode = problem.getRootNode();
        open.add(rootNode);
        while (!open.isEmpty()) {
            PathSearchStep<T, C> currentNode = problem.getNextNode(open);
            if (closed.contains(currentNode.getNode())) {
                continue;
            }
            if (problem.isGoal(currentNode.getNode())) {
                return constructPath(currentNode, parentNodes);
            }
            problem.processNode(currentNode);
            for (PathSearchStep<T, C> adjacent : problem.getAdjacent(currentNode)) {
                problem.processAdjacent(adjacent);
                parentNodes.put(adjacent, currentNode);
                open.add(adjacent);
            }
            closed.add(currentNode.getNode());
        }
        return null;
    }

    private static <T> List<T> constructPath(T goal, Map<T, T> parentNodes) {
        List<T> path = new ArrayList<>();
        path.add(goal);
        T node = goal;
        while ((node = parentNodes.get(node)) != null) {
            path.add(node);
        }
        reverse(path);
        return path;
    }
}
