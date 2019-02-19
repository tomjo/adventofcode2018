package be.tomjo.advent.day23;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day23Test {

    @Test
    void example() {
        String input = "pos=<0,0,0>, r=4\n" +
                "pos=<1,0,0>, r=1\n" +
                "pos=<4,0,0>, r=3\n" +
                "pos=<0,2,0>, r=1\n" +
                "pos=<0,5,0>, r=3\n" +
                "pos=<0,0,3>, r=1\n" +
                "pos=<1,1,1>, r=1\n" +
                "pos=<1,1,2>, r=1\n" +
                "pos=<1,3,1>, r=1";

        Integer result = new Day23().rawPart1(input);

        assertThat(result).isEqualTo(7);
    }

    @Test
    void example_part2() {
        String input = "pos=<10,12,12>, r=2\n" +
                "pos=<12,14,12>, r=2\n" +
                "pos=<16,12,12>, r=4\n" +
                "pos=<14,14,14>, r=6\n" +
                "pos=<50,50,50>, r=200\n" +
                "pos=<10,10,10>, r=5";

        Long result = new Day23().rawPart2(input);

        assertThat(result).isEqualTo(36);
    }
}