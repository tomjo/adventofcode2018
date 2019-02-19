package be.tomjo.advent.day16;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static be.tomjo.advent.util.ArrayUtils.copy;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class CPU {

    @FunctionalInterface
    public interface InstructionMethod {
        void execute(int[] registers, Instruction instruction);
    }

    List<InstructionMethod> getInstructionMethods() {
        return asList(
                this::addR,
                this::addI,
                this::mulR,
                this::mulI,
                this::banR,
                this::banI,
                this::borR,
                this::borI,
                this::eqir,
                this::eqri,
                this::eqrr,
                this::gtir,
                this::gtri,
                this::gtrr,
                this::setR,
                this::setI
        );
    }

    public void execute(List<Instruction> instructions, int[] registers, Map<Integer, InstructionMethod> opcodeInstructionMapping){
        for (Instruction instruction : instructions) {
            execute(registers, instruction, opcodeInstructionMapping.get(instruction.getOpcode()));
        }
    }

    public void execute(int[] registers, Instruction instruction, InstructionMethod instructionMethod) {
        instructionMethod.execute(registers, instruction);
    }

    public boolean matchesInstruction(Sample originalSample, InstructionMethod instructionMethod) {
        Sample sample = new Sample(copy(originalSample.getBefore()), copy(originalSample.getAfter()), originalSample.getInstruction());
        execute(sample.getBefore(), sample.getInstruction(), instructionMethod);
        return Arrays.equals(sample.getBefore(), sample.getAfter());
    }

    private void addR(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] + registers[instruction.getInputB()];
    }

    private void addI(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] + instruction.getInputB();
    }

    private void mulR(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] * registers[instruction.getInputB()];
    }

    private void mulI(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] * instruction.getInputB();
    }

    private void banR(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] & registers[instruction.getInputB()];
    }

    private void banI(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] & instruction.getInputB();
    }

    private void borR(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] | registers[instruction.getInputB()];
    }

    private void borI(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] | instruction.getInputB();
    }

    private void setR(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()];
    }

    private void setI(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = instruction.getInputA();
    }

    private void gtir(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = instruction.getInputA() > registers[instruction.getInputB()] ? 1 : 0;
    }

    private void gtri(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] > instruction.getInputB() ? 1 : 0;
    }

    private void gtrr(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] > registers[instruction.getInputB()] ? 1 : 0;
    }

    private void eqir(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = instruction.getInputA() == registers[instruction.getInputB()] ? 1 : 0;
    }

    private void eqri(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] == instruction.getInputB() ? 1 : 0;
    }

    private void eqrr(int[] registers, Instruction instruction) {
        registers[instruction.getOutput()] = registers[instruction.getInputA()] == registers[instruction.getInputB()] ? 1 : 0;
    }
}
