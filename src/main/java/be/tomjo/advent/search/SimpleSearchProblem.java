package be.tomjo.advent.search;

import lombok.Getter;

import java.util.Collection;
import java.util.Queue;
import java.util.Stack;

@Getter
public class SimpleSearchProblem<T, C, S> implements SearchProblem<T, C, S> {

    private final PathSearchStep<T, C> rootNode;
    private final GoalPredicate<T> goalPredicate;
    private final AdjacencyFunction<T, C, S> adjacentFunction;
    private final NodeStateProcessor<T, C, S> nodeStateProcessor;
    private final NodeStateProcessor<T, C, S> adjacentNodeStateProcessor;
    private final NextNodeChooser<T, C> nextNodeChooser;
    private S state;

    private SimpleSearchProblem(PathSearchStep<T, C> rootNode, GoalPredicate<T> goalPredicate, AdjacencyFunction<T, C, S> adjacentFunction, NodeStateProcessor<T, C, S> nodeStateProcessor, NodeStateProcessor<T, C, S> adjacentNodeStateProcessor, NextNodeChooser<T, C> nextNodeChooser, S state) {
        this.rootNode = rootNode;
        this.goalPredicate = goalPredicate;
        this.adjacentFunction = adjacentFunction;
        this.nodeStateProcessor = nodeStateProcessor;
        this.adjacentNodeStateProcessor = adjacentNodeStateProcessor;
        this.nextNodeChooser = nextNodeChooser;
        this.state = state;
    }

    @Override
    public boolean isGoal(T node) {
        if (goalPredicate == null) {
            return false;
        }
        return goalPredicate.satisfiesGoal(node);
    }

    @Override
    public Collection<PathSearchStep<T, C>> getAdjacent(PathSearchStep<T, C> node) {
        return adjacentFunction.adjacentNodes(node, getState());
    }

    @Override
    public void processNode(PathSearchStep<T, C> node) {
        processNode(node, nodeStateProcessor);
    }

    @Override
    public void processAdjacent(PathSearchStep<T, C> node) {
        processNode(node, adjacentNodeStateProcessor);
    }

    private void processNode(PathSearchStep<T, C> node, NodeStateProcessor<T, C, S> nodeStateProcessor) {
        if (nodeStateProcessor != null) {
            this.state = nodeStateProcessor.process(node, state);
        }
    }

    @Override
    public PathSearchStep<T, C> getNextNode(Collection<PathSearchStep<T, C>> container) {
        if (nextNodeChooser != null) {
            return nextNodeChooser.chooseNextNode(container);
        }
        if (container instanceof Queue) {
            return ((Queue<PathSearchStep<T, C>>) container).poll();
        }
        if (container instanceof Stack) {
            return ((Stack<PathSearchStep<T, C>>) container).pop();
        }
        throw new IllegalStateException("error.search.node.next");
    }

    public static class SimpleSearchProblemBuilder<T, C, S> {

        public static <T, C, S> SimpleSearchProblemBuilder<T, C, S> searchProblem() {
            return new SimpleSearchProblemBuilder<>();
        }

        public static <T, C, S> SimpleSearchProblemBuilder<T, C, S> searchProblem(Class<T> t, Class<C> c, Class<S> s) {
            return new SimpleSearchProblemBuilder<>();
        }

        private PathSearchStep<T, C> rootNode;
        private GoalPredicate<T> goalPredicate;
        private AdjacencyFunction<T, C, S> adjacentFunction;
        private NodeStateProcessor<T, C, S> nodeStateProcessor;
        private NodeStateProcessor<T, C, S> adjacentNodeStateProcessor;
        private NextNodeChooser<T, C> nextNodeChooser;
        private S state;

        SimpleSearchProblemBuilder() {
        }

        public SimpleSearchProblemBuilder<T, C, S> rootNode(T rootNode, C rootNodeCost) {
            this.rootNode = new PathSearchStep<>(rootNode, rootNodeCost);
            return this;
        }

        public SimpleSearchProblemBuilder<T, C, S> goalPredicate(GoalPredicate<T> goalPredicate) {
            this.goalPredicate = goalPredicate;
            return this;
        }

        public SimpleSearchProblemBuilder<T, C, S> adjacentFunction(AdjacencyFunction<T, C, S> adjacentFunction) {
            this.adjacentFunction = adjacentFunction;
            return this;
        }

        public SimpleSearchProblemBuilder<T, C, S> nodeStateProcessor(NodeStateProcessor<T, C, S> nodeStateProcessor) {
            this.nodeStateProcessor = nodeStateProcessor;
            return this;
        }

        public SimpleSearchProblemBuilder<T, C, S> adjacentNodeStateProcessor(NodeStateProcessor<T, C, S> adjacentNodeStateProcessor) {
            this.adjacentNodeStateProcessor = adjacentNodeStateProcessor;
            return this;
        }

        public SimpleSearchProblemBuilder<T, C, S> nextNodeChooser(NextNodeChooser<T, C> nextNodeChooser) {
            this.nextNodeChooser = nextNodeChooser;
            return this;
        }

        public SimpleSearchProblemBuilder<T, C, S> initialState(S state) {
            this.state = state;
            return this;
        }

        public SimpleSearchProblem<T, C, S> build() {
            return new SimpleSearchProblem<>(rootNode, goalPredicate, adjacentFunction, nodeStateProcessor, adjacentNodeStateProcessor, nextNodeChooser, state);
        }
    }
}
