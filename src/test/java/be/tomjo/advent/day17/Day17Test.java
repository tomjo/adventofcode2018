package be.tomjo.advent.day17;

import be.tomjo.advent.math.V2;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {


    @Test
    void example_part1() {
        String input = "x=495, y=2..7\n" +
                "y=7, x=495..501\n" +
                "x=501, y=3..7\n" +
                "x=498, y=2..4\n" +
                "x=506, y=1..2\n" +
                "x=498, y=10..13\n" +
                "x=504, y=10..13\n" +
                "y=13, x=498..504";

        Integer result = new Day17().rawPart1(input);

        assertThat(result).isEqualTo(57);
    }

    @Test
    void example_part2() {
        String input = "x=495, y=2..7\n" +
                "y=7, x=495..501\n" +
                "x=501, y=3..7\n" +
                "x=498, y=2..4\n" +
                "x=506, y=1..2\n" +
                "x=498, y=10..13\n" +
                "x=504, y=10..13\n" +
                "y=13, x=498..504";

        Integer result = new Day17().rawPart2(input);

        assertThat(result).isEqualTo(29);
    }
}