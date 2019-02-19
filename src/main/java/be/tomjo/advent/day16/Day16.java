package be.tomjo.advent.day16;

import be.tomjo.advent.Solution;
import lombok.Value;

import java.util.*;

import static be.tomjo.advent.util.ArrayUtils.copy;
import static be.tomjo.advent.util.StringUtils.lines;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Day16 implements Solution<Integer, Integer, Day16.Day16Input> {

    public static void main(String[] args) {
        new Day16().run();
    }

    @Override
    public Integer part1(Day16Input input) {
        List<Sample> samples = input.getSamples();
        CPU cpu = new CPU();
        int total = 0;
        for (Sample sample : samples) {
            int matchingCount = 0;
            for (CPU.InstructionMethod instructionMethod : cpu.getInstructionMethods()) {
                int[] registers = copy(sample.getBefore());
                cpu.execute(registers, sample.getInstruction(), instructionMethod);
                if(Arrays.equals(registers, sample.getAfter())){
                    matchingCount++;
                }
            }
            if (matchingCount >= 3) {
                total++;
            }
        }
        return total;
    }

    @Override
    public Integer part2(Day16Input input) {
        CPU cpu = new CPU();
        Map<Integer, CPU.InstructionMethod> opcodeInstructionMapping = determineOpcodeInstructionMapping(cpu, input.getSamples());
        int[] registers = new int[4];
        cpu.execute(input.getProgram(), registers, opcodeInstructionMapping);
        return registers[0];
    }

    private Map<Integer, CPU.InstructionMethod> determineOpcodeInstructionMapping(CPU cpu, List<Sample> samples) {
        Map<Integer, CPU.InstructionMethod> opcodeInstructionMapping = new HashMap<>();
        List<CPU.InstructionMethod> instructionMethods = new ArrayList<>(cpu.getInstructionMethods());
        for (Sample sample : samples) {
            List<CPU.InstructionMethod> matchingInstructions = instructionMethods.stream()
                    .filter(im -> cpu.matchesInstruction(sample, im))
                    .collect(toList());
            if (matchingInstructions.size() == 1) {
                opcodeInstructionMapping.put(sample.getInstruction().getOpcode(), matchingInstructions.get(0));
                instructionMethods.remove(matchingInstructions.get(0));
            }
        }
        return opcodeInstructionMapping;
    }


    @Override
    public Day16Input parse(String input) {
        List<Sample> samples = new ArrayList<>();
        List<String> lines = lines(input);
        List<Instruction> instructions = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("Before:")) {
                samples.add(parseSample(lines, i));
                i += 2;
            } else {
                instructions.add(parseInstruction(lines.get(i)));
            }
        }
        return new Day16Input(samples, instructions);
    }

    private Sample parseSample(List<String> lines, int sampleIdx) {
        int[] before = parseBefore(lines.get(sampleIdx));
        Instruction instruction = parseInstruction(lines.get(sampleIdx + 1));
        int[] after = parseAfter(lines.get(sampleIdx + 2));
        return new Sample(before, after, instruction);
    }

    private int[] parseAfter(String s) {
        String[] parts;
        parts = s.split(", ");
        parts[0] = parts[0].substring("After:  [".length());
        parts[3] = parts[3].substring(0, parts[3].length() - 1);
        return stream(parts).mapToInt(Integer::parseInt).toArray();
    }

    private Instruction parseInstruction(String s) {
        int[] instruction = stream(s.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        return new Instruction(instruction[0], instruction[1], instruction[2], instruction[3]);
    }

    private int[] parseBefore(String line) {
        String[] parts = line.split(", ");
        parts[0] = parts[0].substring("Before: [".length());
        parts[3] = parts[3].substring(0, parts[3].length() - 1);
        return stream(parts).mapToInt(Integer::parseInt).toArray();
    }

    @Value
    static class Day16Input {
        List<Sample> samples;
        List<Instruction> program;
    }

}













