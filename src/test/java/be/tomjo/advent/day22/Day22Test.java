package be.tomjo.advent.day22;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day22Test {

    @Test
    void example_part1() {
        String input = "depth: 510\n" +
                "target: 10,10\n";

        long result = new Day22().rawPart1(input);

        assertThat(result).isEqualTo(114);
    }

    @Test
    void example_part2() {
        String input = "depth: 510\n" +
                "target: 10,10\n";

        Integer result = new Day22().rawPart2(input);

        assertThat(result).isEqualTo(45);
    }
}