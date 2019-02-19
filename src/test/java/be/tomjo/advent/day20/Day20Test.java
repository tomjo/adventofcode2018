package be.tomjo.advent.day20;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test {

    @Test
    void example() {
        String input = "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$";

        Integer result = new Day20().rawPart1(input);

        assertThat(result).isEqualTo(23);
    }
    @Test
    void example4() {
        String input = "^WNE$";

        Integer result = new Day20().rawPart1(input);

        assertThat(result).isEqualTo(3);
    }
    @Test
    void example5() {
        String input = "^ENWWW(NEEE|SSE(EE|N))$";

        Integer result = new Day20().rawPart1(input);

        assertThat(result).isEqualTo(10);
    }

    @Test
    void example2() {
        String input = "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$";

        Integer result = new Day20().rawPart1(input);

        assertThat(result).isEqualTo(18);
    }

    @Test
    void example3() {
        String input = "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$";

        Integer result = new Day20().rawPart1(input);

        assertThat(result).isEqualTo(31);
    }
}