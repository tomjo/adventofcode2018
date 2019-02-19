package be.tomjo.advent.search;

@FunctionalInterface
public interface GoalPredicate<T> {
    boolean satisfiesGoal(T node);
}
