package be.tomjo.advent.search;

@FunctionalInterface
public interface NodeStateProcessor<T, C, S> {
    S process(PathSearchStep<T,C> node, S state);
}
