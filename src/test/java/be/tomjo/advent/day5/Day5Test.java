package be.tomjo.advent.day5;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day5Test {

    @Test
    void example() {
        String input = "dabAcCaCBAcCcaDA";

        int result = new Day5().rawPart1(input);

        assertThat(result).isEqualTo(10);
    }

    @Test
    void example2() {
        String input = "dabAcCaCBAcCcaDA";

        int result = new Day5().rawPart2(input);

        assertThat(result).isEqualTo(4);
    }


}