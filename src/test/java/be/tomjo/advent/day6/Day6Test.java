package be.tomjo.advent.day6;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {


    @Test
    void example() {
        String input = "1, 1\n" +
                "1, 6\n" +
                "8, 3\n" +
                "3, 4\n" +
                "5, 5\n" +
                "8, 9";

        int result = new Day6().rawPart1(input);

        assertThat(result).isEqualTo(17);
    }

    @Test
    void example2() {
        String input = "1, 1\n" +
                "1, 6\n" +
                "8, 3\n" +
                "3, 4\n" +
                "5, 5\n" +
                "8, 9";
        Day6.DISTANCE_LIMIT = 32;

        int result = new Day6().rawPart2(input);

        assertThat(result).isEqualTo(16);
    }
}