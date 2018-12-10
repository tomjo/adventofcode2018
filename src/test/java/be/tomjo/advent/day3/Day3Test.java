package be.tomjo.advent.day3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {

    @Test
    void part1() {
        String input = "#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2";

        int result = new Day3().rawPart1(input);

        assertThat(result).isEqualTo(4);
    }

    @Test
    void part2() {
        String input = "#1 @ 1,3: 4x4\n#2 @ 3,1: 4x4\n#3 @ 5,5: 2x2";

        int result = new Day3().rawPart2(input);

        assertThat(result).isEqualTo(3);
    }
}