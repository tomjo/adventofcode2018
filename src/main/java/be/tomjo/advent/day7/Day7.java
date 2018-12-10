package be.tomjo.advent.day7;

import be.tomjo.advent.Solution;

import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Day7 implements Solution<String, Integer, DAG> {
    private static final Pattern EDGE_REGEX = Pattern.compile("^Step (.+) must be finished before step (.+) can begin.$");

    public static int WORKER_COUNT = 5;
    public static int BASE_STEP_DURATION = 60;

    public static void main(String[] args) {
        new Day7().run();
    }

    @Override
    public DAG parse(String input) {
        return inputToGraph(input);
    }

    @Override
    public String part1(DAG graph) {
        List<Character> topologicalSort = graph.getTopologicalSort(PriorityQueue::new);
        return topologicalSort.stream()
                .map(String::valueOf)
                .collect(joining());
    }

    @Override
    public Integer part2(DAG graph) {
        Scheduler scheduler = new Scheduler(graph, WORKER_COUNT, BASE_STEP_DURATION, Day7::durationOf);
        return scheduler.completeTasks();
    }

    private static int durationOf(char task) {
        return task - 'A' + 1;
    }

    private static DAG inputToGraph(String input) {
        List<Edge> edges = lines(input).stream()
                .map(Day7::parseEdge)
                .collect(toList());
        return new DAG(edges);
    }

    private static Edge parseEdge(String s) {
        Matcher matcher = EDGE_REGEX.matcher(s);
        if (!matcher.find()) {
            throw new IllegalStateException();
        }
        char a = matcher.group(1).charAt(0);
        char b = matcher.group(2).charAt(0);
        return new Edge(a, b);
    }
}
