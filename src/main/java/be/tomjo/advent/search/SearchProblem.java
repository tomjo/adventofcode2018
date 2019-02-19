package be.tomjo.advent.search;

import java.util.Collection;

public interface SearchProblem<T,C,S> {
    PathSearchStep<T,C> getRootNode();

    boolean isGoal(T node);

    Collection<PathSearchStep<T,C>> getAdjacent(PathSearchStep<T,C> node);

    void processNode(PathSearchStep<T,C> node);
    void processAdjacent(PathSearchStep<T,C> node);

    S getState();

    PathSearchStep<T,C> getNextNode(Collection<PathSearchStep<T,C>> container);
}
