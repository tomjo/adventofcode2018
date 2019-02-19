package be.tomjo.advent.day22;

import be.tomjo.advent.search.PathSearchStep;
import be.tomjo.advent.math.V2;
import be.tomjo.advent.search.AdjacencyFunction;
import be.tomjo.advent.search.SimpleSearchProblem;
import be.tomjo.advent.search.NodeStateProcessor;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static be.tomjo.advent.search.PathFinder.findShortestPath;
import static be.tomjo.advent.util.ArrayUtils.indexOf;
import static be.tomjo.advent.util.CollectionUtil.last;
import static be.tomjo.advent.util.CollectionUtil.not;
import static be.tomjo.advent.util.MathUtil.manhattanDistanceToPoint;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class CaveExploration {

    private final CaveSystem caveSystem;

    public int findShortestStepsToTarget() {
        SimpleSearchProblem<CavePathNode, Integer, Map<CavePathNode, Integer>> prob = createCaveTargetFindingProblem(caveSystem.getTargetLocation());
        return last(findShortestPath(prob, createQueuePreferringMinimalTravelTime(caveSystem.getTargetLocation())))
                .map(PathSearchStep::getCost)
                .orElseThrow(IllegalStateException::new);
    }

    private SimpleSearchProblem<CavePathNode, Integer, Map<CavePathNode, Integer>> createCaveTargetFindingProblem(V2 targetLocation) {
        return SimpleSearchProblem.SimpleSearchProblemBuilder.<CavePathNode, Integer, Map<CavePathNode, Integer>>searchProblem()
                .rootNode(new CavePathNode(new V2(0, 0), Tool.TORCH), 0)
                .initialState(new HashMap<>())
                .goalPredicate(new CavePathNode(targetLocation, Tool.TORCH)::equals)
                .adjacentFunction(getAdjacentRoomsWithLowerTotalCost())
                .adjacentNodeStateProcessor(addNodeToState())
                .build();
    }

    private NodeStateProcessor<CavePathNode, Integer, Map<CavePathNode, Integer>> addNodeToState() {
        return (node, state) -> {
            state.put(node.getNode(), node.getCost());
            return (Map<CavePathNode, Integer>) state;
        };
    }

    private AdjacencyFunction<CavePathNode, Integer, Map<CavePathNode, Integer>> getAdjacentRoomsWithLowerTotalCost() {
        return (current, state) -> getReachableAdjacentRegions(current.getNode()).stream()
                .filter(neighbour -> current.getCost() + neighbour.getCost() < state.getOrDefault(neighbour.getNode(), Integer.MAX_VALUE))
                .map(n -> new PathSearchStep<>(n.getNode(), n.getCost() + current.getCost()))
                .collect(Collectors.<PathSearchStep<CavePathNode, Integer>>toList());
    }

    private Supplier<Collection<PathSearchStep<CavePathNode, Integer>>> createQueuePreferringMinimalTravelTime(V2 target) {
        return () -> new PriorityQueue<>(Comparator.<PathSearchStep<CavePathNode, Integer>>comparingInt(PathSearchStep::getCost)
                .thenComparingInt(c -> manhattanDistanceToPoint(target, c.getNode().getLocation())));
    }

    private Collection<PathSearchStep<CavePathNode, Integer>> getReachableAdjacentRegions(CavePathNode node) {
        return node.getLocation().getVerticalAndHorizontalNeighbours().stream()
                .filter(not(this::isCaveLocationOutOfBounds))
                .map(location -> getCaveNodesReachableWithTools(location, node.getTool())).flatMap(Collection::stream)
                .map(caveNode -> addCost(caveNode, node.getTool()))
                .collect(toList());
    }

    private PathSearchStep<CavePathNode, Integer> addCost(CavePathNode node, Tool currentlyEquipedTool) {
        return new PathSearchStep<>(node, currentlyEquipedTool != node.getTool() ? 8 : 1);
    }

    private Collection<CavePathNode> getCaveNodesReachableWithTools(V2 location, Tool currentlyEquipedTool) {
        RegionType type = caveSystem.getRegionType(location);
        return stream(Tool.values())
                .filter(tool -> isToolUsableForRegionType(type, tool)
                        && isToolUsableForRegionType(type, currentlyEquipedTool))
                .map(tool -> new CavePathNode(location, tool))
                .collect(toList());
    }

    private boolean isToolUsableForRegionType(RegionType type, Tool tool) {
        return !type.getNonUseableTool().equals(tool);
    }

    private boolean isCaveLocationOutOfBounds(V2 neighbour) {
        return neighbour.getXAsInt() < 0 || neighbour.getYAsInt() < 0;
    }

}
