package be.tomjo.advent.day9;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    @Test
    void examples() {
        assertThat(new Day9().rawPart1("9 players; last marble is worth 25 points")).isEqualTo(32);
        assertThat(new Day9().rawPart1("10 players; last marble is worth 1618 points")).isEqualTo(8317);
        assertThat(new Day9().rawPart1("13 players; last marble is worth 7999 points")).isEqualTo(146373);
        assertThat(new Day9().rawPart1("17 players; last marble is worth 1104 points")).isEqualTo(2764);
        assertThat(new Day9().rawPart1("21 players; last marble is worth 6111 points")).isEqualTo(54718);
        assertThat(new Day9().rawPart1("30 players; last marble is worth 5807 points")).isEqualTo(37305);
    }
}