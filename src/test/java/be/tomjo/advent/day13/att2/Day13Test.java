package be.tomjo.advent.day13.att2;

import be.tomjo.advent.day13.Day13;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {


    @Test
    void example() {
        String input = "/->-\\        \n" +
                "|   |  /----\\\n" +
                "| /-+--+-\\  |\n" +
                "| | |  | v  |\n" +
                "\\-+-/  \\-+--/\n" +
                "  \\------/   ";

        String result = new Day13().rawPart1(input);

        assertThat(result).isEqualTo("7,3");
    }


    @Test
    void example2() {
        String input = "/>-<\\  \n" +
                "|   |  \n" +
                "| /<+-\\\n" +
                "| | | v\n" +
                "\\>+</ |\n" +
                "  |   ^\n" +
                "  \\<->/";

        String result = new Day13().rawPart2(input);

        assertThat(result).isEqualTo("6,4");
    }


}
