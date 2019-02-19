package be.tomjo.advent.search;

import java.util.Collection;

@FunctionalInterface
public interface AdjacencyFunction<T, C, S> {
    Collection<PathSearchStep<T,C>> adjacentNodes(PathSearchStep<T,C> node, S state);
}
