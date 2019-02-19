package be.tomjo.advent.day14;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day14Test {


    @Test
    void example() {
        assertThat(new Day14().rawPart1("9")).isEqualTo("5158916779");
        assertThat(new Day14().rawPart1("18")).isEqualTo("9251071085");
        assertThat(new Day14().rawPart1("2018")).isEqualTo("5941429882");
        assertThat(new Day14().rawPart1("5")).isEqualTo("0124515891");
    }

    @Test
    void example2() {
        assertThat(new Day14().rawPart2("51589")).isEqualTo(9);
        assertThat(new Day14().rawPart2("59414")).isEqualTo(2018);
        assertThat(new Day14().rawPart2("92510")).isEqualTo(18);
        assertThat(new Day14().rawPart2("01245")).isEqualTo(5);
    }
}