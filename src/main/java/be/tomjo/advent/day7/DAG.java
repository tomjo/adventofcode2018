package be.tomjo.advent.day7;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class DAG {

    private final Collection<Edge> edges;

    public DAG(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Character> getNodesWithoutIncomingEdge() {
        return edges.stream()
                .map(Edge::getFrom)
                .distinct()
                .filter(a -> edges.stream().map(Edge::getTo).allMatch(b -> a != b))
                .sorted()
                .collect(toList());
    }

    public List<Character> getTopologicalSort(Supplier<Queue<Character>> queueSupplier) {
        List<Character> topologicalSort = new ArrayList<>();
        Queue<Character> stack = queueSupplier.get();
        stack.addAll(getNodesWithoutIncomingEdge());
        Map<Character, Collection<Edge>> incomingEdgesMap = new HashMap<>();
        while (!stack.isEmpty()) {
            Character node = stack.poll();
            topologicalSort.add(node);
            for (Edge outgoingEdge : getOutgoingEdges(node)) {
                Collection<Edge> incomingEdges = incomingEdgesMap.computeIfAbsent(outgoingEdge.getTo(), this::getIncomingEdges);
                incomingEdges.remove(outgoingEdge);
                if (incomingEdges.isEmpty()) {
                    stack.add(outgoingEdge.getTo());
                }
            }
        }
        return topologicalSort;
    }

    public Collection<Edge> getOutgoingEdges(char node) {
        return edges.stream()
                .filter(e -> e.getFrom() == node)
                .sorted(comparingInt(Edge::getTo))
                .collect(toList());
    }

    public Collection<Edge> getIncomingEdges(char node) {
        return edges.stream()
                .filter(e -> e.getTo() == node)
                .sorted(comparingInt(Edge::getFrom))
                .collect(toList());
    }

    public Collection<Edge> getAllIncomingEdges(char node) {
        Set<Edge> incoming = new HashSet<>();
        Collection<Edge> incomingEdges = getIncomingEdges(node);
        incoming.addAll(incomingEdges);
        for (Edge edge : incomingEdges) {
            incoming.addAll(getAllIncomingEdges(edge.getFrom()));
        }
        return incoming;
    }

}
