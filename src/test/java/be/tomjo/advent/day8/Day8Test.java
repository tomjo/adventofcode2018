package be.tomjo.advent.day8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day8Test {

    @Test
    void example() {
        String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

        int result = new Day8().rawPart1(input);

        assertThat(result).isEqualTo(138);
    }

    @Test
    void example2() {
        String input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";

        int result = new Day8().rawPart2(input);

        assertThat(result).isEqualTo(66);
    }
}