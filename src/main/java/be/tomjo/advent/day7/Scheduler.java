package be.tomjo.advent.day7;

import java.util.*;
import java.util.function.Function;

import static be.tomjo.advent.util.CollectionUtil.not;
import static be.tomjo.advent.util.CollectionUtil.throwingMerger;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

public class Scheduler {

    private final int baseTaskDuration;
    private final Function<Character, Integer> taskCostFunction;
    private final Map<Character, Integer> tasksInProgress;
    private int availableWorkers;

    private final Map<Character, Collection<Edge>> requiredTasks;

    public Scheduler(DAG taskGraph, int workerCount, int baseTaskDuration, Function<Character, Integer> taskCostFunction) {
        this.availableWorkers = workerCount;
        this.baseTaskDuration = baseTaskDuration;
        this.taskCostFunction = taskCostFunction;
        this.requiredTasks = taskGraph.getTopologicalSort(PriorityQueue::new)
                .stream()
                .collect(toMap(Function.identity(), taskGraph::getAllIncomingEdges, throwingMerger(), LinkedHashMap::new));
        this.tasksInProgress = new HashMap<>();
    }

    public int completeTasks() {
        int time = 0;
        while (!requiredTasks.isEmpty() || !tasksInProgress.isEmpty()) {
            startNewTasksIfPossible();
            workOnTasks();
            time++;
        }
        return time;
    }

    private void workOnTasks() {
        new ArrayList<>(tasksInProgress.keySet())
                .forEach(k -> tasksInProgress.merge(k, -1, this::mergeTaskDuration));
    }

    private void startNewTasksIfPossible() {
        if (availableWorkers > 0) {
            range(0, availableWorkers)
                    .forEach(i -> getNextTask()
                            .ifPresent(this::startNewTask));
        }
    }

    private void startNewTask(char nextTask) {
        requiredTasks.remove(nextTask);
        availableWorkers--;
        tasksInProgress.put(nextTask, baseTaskDuration + taskCostFunction.apply(nextTask));
    }

    private Integer mergeTaskDuration(int a, int b) {
        int sum = a + b;
        if (a + b == 0) {
            availableWorkers++;
            return null;
        }
        return sum;
    }

    private Optional<Character> getNextTask() {
        return requiredTasks.keySet().stream()
                .filter(not(this::nextTaskRequiresTaskInProgress))
                .findFirst();
    }

    private boolean nextTaskRequiresTaskInProgress(char nextTask) {
        return requiredTasks.get(nextTask)
                .stream()
                .map(Edge::getFrom)
                .anyMatch(tasksInProgress::containsKey);
    }
}
