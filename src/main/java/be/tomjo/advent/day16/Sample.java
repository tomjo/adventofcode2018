package be.tomjo.advent.day16;

import lombok.Value;

@Value
public class Sample {
    int[] before;
    int[] after;
    Instruction instruction;
}
