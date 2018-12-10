package be.tomjo.advent.day2;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    @Test
    void checksum() {
        int result = new Day2().rawPart1("abcdef\nbababc\nabbcde\nabcccd\naabcdd\nabcdee\nababab\n");

        assertThat(result).isEqualTo(12);
    }

    @Test
    void match() {
        String result = new Day2().rawPart2("abcde\nfghij\nklmno\npqrst\nfguij\naxcye\nwvxyz\n");

        assertThat(result).isEqualTo("fgij");
    }
}