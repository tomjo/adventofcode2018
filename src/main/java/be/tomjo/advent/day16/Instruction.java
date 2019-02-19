package be.tomjo.advent.day16;

import lombok.Value;

@Value
public class Instruction {
    int opcode;
    int inputA;
    int inputB;
    int output;
}
