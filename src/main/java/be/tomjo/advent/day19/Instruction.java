package be.tomjo.advent.day19;

import lombok.Value;

@Value
public class Instruction {

    private final String instruction;
    private final int inputA;
    private final int inputB;
    private final int output;
}
