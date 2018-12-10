package be.tomjo.advent.day1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    @Test
    void frequence() {
        assertThat(new Day1().rawPart1("+1\n-2\n+3\n+1")).isEqualTo(3);
        assertThat(new Day1().rawPart1("+1\n+1\n-2")).isEqualTo(0);
        assertThat(new Day1().rawPart1("-1\n-2\n-3")).isEqualTo(-6);
    }

    @Test
    void frequency2() {
        assertThat(new Day1().rawPart2("+1\n-1")).isEqualTo(0);
        assertThat(new Day1().rawPart2("+3\n+3\n+4\n-2\n-4")).isEqualTo(10);
        assertThat(new Day1().rawPart2("+7\n+7\n-2\n-7\n-4")).isEqualTo(14);
        assertThat(new Day1().rawPart2("-6\n+3\n+8\n+5\n-6")).isEqualTo(5);
    }
}