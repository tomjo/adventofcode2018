package be.tomjo.advent.day19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

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

    private Map<String, InstructionMethod> methods() {
        Map<String, InstructionMethod> methods = new HashMap<>();
        methods.put("addr", this::addR);
        methods.put("addi", this::addI);
        methods.put("mulr", this::mulR);
        methods.put("muli", this::mulI);
        methods.put("banr", this::banR);
        methods.put("bani", this::banI);
        methods.put("borr", this::borR);
        methods.put("bori", this::borI);
        methods.put("eqir", this::eqir);
        methods.put("eqri", this::eqri);
        methods.put("eqrr", this::eqrr);
        methods.put("gtir", this::gtir);
        methods.put("gtri", this::gtri);
        methods.put("gtrr", this::gtrr);
        methods.put("setr", this::setR);
        methods.put("seti", this::setI);
        return methods;
    }

    public void execute(Instruction instruction, int[] registers) {
        methods().get(instruction.getInstruction()).execute(registers,instruction);
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
