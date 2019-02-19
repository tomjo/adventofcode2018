package be.tomjo.advent.day20;

import be.tomjo.advent.Solution;
import be.tomjo.advent.math.V2;
import be.tomjo.advent.search.NodeStateProcessor;
import be.tomjo.advent.search.PathSearchStep;
import be.tomjo.advent.search.SearchProblem;

import java.util.ArrayDeque;
import java.util.Deque;

import static be.tomjo.advent.search.PathFinder.findShortestPath;
import static be.tomjo.advent.search.SimpleSearchProblem.SimpleSearchProblemBuilder.searchProblem;
import static be.tomjo.advent.util.StringUtils.lines;
import static java.util.stream.Collectors.toList;

public class Day20 implements Solution<Integer, Integer, Room> {

    public static void main(String[] args) {
        new Day20().run();
    }

    @Override
    public Room parse(String input) {
        String regex = lines(input).get(0);
        String directionsRegex = regex.substring(1, regex.length() - 1);
        return exploreFacility(directionsRegex);
    }

    @Override
    public Integer part1(Room startingRoom) {
        SearchProblem<Room, Integer, Integer> problem = createRoomTraversalProblem(startingRoom, this::getHighestDoorCount);
        findShortestPath(problem, () -> new ArrayDeque<>());
        return problem.getState();
    }

    private int getHighestDoorCount(PathSearchStep<Room, Integer> node, int doorCount) {
        return Math.max(doorCount, node.getCost());
    }

    @Override
    public Integer part2(Room startingRoom) {
        SearchProblem<Room, Integer, Integer> problem = createRoomTraversalProblem(startingRoom, this::getRoomCountWherePathContainsAtleast1000Doors);
        findShortestPath(problem, () -> new ArrayDeque<>());
        return problem.getState();
    }

    private int getRoomCountWherePathContainsAtleast1000Doors(PathSearchStep<Room, Integer> node, int roomCount) {
        return node.getCost() >= 1000 ? roomCount + 1 : roomCount;
    }

    private Room exploreFacility(String input) {
        Room startingRoom = new Room(new V2(0, 0));
        Room currentRoom = startingRoom;
        Deque<Room> stack = new ArrayDeque<>();
        for (char c : input.toCharArray()) {
            if (c == 'W') {
                currentRoom = currentRoom.addWestRoom();
            } else if (c == 'E') {
                currentRoom = currentRoom.addEastRoom();
            } else if (c == 'S') {
                currentRoom = currentRoom.addSouthRoom();
            } else if (c == 'N') {
                currentRoom = currentRoom.addNorthRoom();
            } else if (c == '(') {
                stack.push(currentRoom);
            } else if (c == '|') {
                currentRoom = peekOrThrow(stack);
            } else if (c == ')') {
                currentRoom = stack.pop();
            }
        }
        return startingRoom;
    }

    private Room peekOrThrow(Deque<Room> stack) {
        if(stack.isEmpty()){
            throw new IllegalStateException();
        }
        return stack.peek();
    }

    private SearchProblem<Room, Integer, Integer> createRoomTraversalProblem(Room startingRoom,
                                                                             NodeStateProcessor<Room, Integer, Integer> nodeStateProcessor) {
        return searchProblem(Room.class, Integer.class, Integer.class)
                .initialState(0)
                .rootNode(startingRoom, 0)
                .adjacentFunction((r, s) -> r.getNode().getAdjacentConnectedRooms().stream()
                        .map(n -> new PathSearchStep<>(n, r.getCost() + 1))
                        .collect(toList()))
                .nodeStateProcessor(nodeStateProcessor)
                .build();
    }

}










