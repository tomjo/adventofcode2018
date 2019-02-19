package be.tomjo.advent.search;

import lombok.Value;

@Value
public class PathSearchStep<T,C> {
    private final T node;
    private final C cost;
}
