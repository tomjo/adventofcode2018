package be.tomjo.advent.search;

import java.util.Collection;

@FunctionalInterface
public interface NextNodeChooser<T, C> {
    PathSearchStep<T,C> chooseNextNode(Collection<PathSearchStep<T,C>> collection);
}
