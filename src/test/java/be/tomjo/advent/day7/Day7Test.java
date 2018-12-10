package be.tomjo.advent.day7;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {

    @Test
    void example() {
        String input = "Step C must be finished before step A can begin.\n" +
                "Step C must be finished before step F can begin.\n" +
                "Step A must be finished before step B can begin.\n" +
                "Step A must be finished before step D can begin.\n" +
                "Step B must be finished before step E can begin.\n" +
                "Step D must be finished before step E can begin.\n" +
                "Step F must be finished before step E can begin.";

        String result =new Day7().rawPart1(input);

        assertThat(result).isEqualTo("CABDFE");
    }

    @Test
    void example2() {
        String input = "Step C must be finished before step A can begin.\n" +
                "Step C must be finished before step F can begin.\n" +
                "Step A must be finished before step B can begin.\n" +
                "Step A must be finished before step D can begin.\n" +
                "Step B must be finished before step E can begin.\n" +
                "Step D must be finished before step E can begin.\n" +
                "Step F must be finished before step E can begin.";
        Day7.WORKER_COUNT = 2;
        Day7.BASE_STEP_DURATION = 0;

        int result = new Day7().rawPart2(input);

        assertThat(result).isEqualTo(15);
    }
}