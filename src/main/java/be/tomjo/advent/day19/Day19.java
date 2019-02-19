package be.tomjo.advent.day19;

import be.tomjo.advent.Solution;
import lombok.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Integer.parseInt;

public class Day19 implements Solution<Integer, Integer, Day19.D19Input> {

    public static void main(String[] args) {
        new Day19().run();
    }

    @Override
    public D19Input parse(String input) {
        List<String> lines = lines(input);
        int ipRegister = parseInt(lines.get(0).substring(4));
        List<Instruction> instructions = lines.subList(1, lines.size()).stream()
                .map(line -> line.split(" "))
                .map(p -> new Instruction(p[0], parseInt(p[1]), parseInt(p[2]), parseInt(p[3])))
                .collect(Collectors.toList());
        return new D19Input(instructions, ipRegister);
    }

    @Override
    public Integer part1(D19Input input) {
        int ipRegister = input.getIpRegister();
        int[] registers = new int[6];
        int ip = 0;
        CPU cpu = new CPU();
        List<Instruction> instructions = input.getInstructions();
        try {
            for (; ; ) {
                Instruction nextInstruction = instructions.get(ip);
                registers[ipRegister] = ip;
                cpu.execute(nextInstruction, registers);
                ip = registers[ipRegister];
                ip++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return registers[0];
    }

    //ip goes 3 -> 11 repeat
    //3..4..
    //eqrr 2 4 2
    //5-9
    //gtrr 5 4 2
    //10..11

    //eqq [2] increaes
    //gt [5] increase

    //register5 incremented at ip 8
    //after ip 3: register2 = register5
    @Override
    public Integer part2(D19Input input) {
        int ipRegister = input.getIpRegister();
        int[] registers = new int[6];
        registers[0] = 1;
        int ip = 0;
        CPU cpu = new CPU();
        List<Instruction> instructions = input.getInstructions();
        Map<Integer, Integer> register0Values = new HashMap<>();
        int i = 0;

        registers[0] = 0;
        registers[1] = 10551347;
        registers[2] = 0;
        registers[3] = 8;
        registers[4] = 10551347;
        registers[5] = 10551347;
        ip=8;
        try {
            for (; ; ) {
                Instruction nextInstruction = instructions.get(ip);
                registers[ipRegister] = ip;
//                if(ip == 8){
//                    registers[5] = 10551348;
//                }
//                if(nextInstruction.getInstruction().startsWith("gt") || nextInstruction.getInstruction().startsWith("eq")){
//                    System.out.println(nextInstruction.getInstruction()+" "+nextInstruction.getInputA()+" "+nextInstruction.getInputB()+" "+nextInstruction.getOutput());
//                    System.out.println(registers[0]+" "+registers[1]+" "+registers[2]+" "+registers[3]+" "+registers[4]+" "+registers[5]);
////                    registers[5] = 10551347;
//                }
//                if (nextInstruction.getInstruction().startsWith("eq")) {
//                    System.out.println(nextInstruction.getInstruction() + " " + nextInstruction.getInputA() + " " + nextInstruction.getInputB() + " " + nextInstruction.getOutput());
//                    System.out.println(registers[0] + " " + registers[1] + " " + registers[2] + " " + registers[3] + " " + registers[4] + " " + registers[5]);
//                    registers[2] = 10551348;
////                    registers[5] = 10551347;
//                }
                System.out.println(i+" "+ip+" "+hash(registers));
                cpu.execute(nextInstruction, registers);
                ip = registers[ipRegister];
                ip++;
                i++;
                if(i > 10){
                    break;
                }
//                System.out.println(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return registers[0];
    }

    private String hash(int[] registers) {
        return registers[0]+" "+registers[1]+" "+registers[2]+" "+registers[3]+" "+registers[4]+" "+registers[5] + "";
    }

    @Value
    static class D19Input {
        List<Instruction> instructions;
        int ipRegister;
    }

//    mulr 1 5 2 r[1] * r[5] -> r[2]
//    eqrr 2 4 2 r[2] == r[4] ? -> r[2] = 1 | 0
//    addr 2 3 3 r[2] + r[3] -> r[3]
//    addi 3 1 3 r[3]++
//    addr 1 0 0 r[1]+r[0] -> r[0]
//    addi 5 1 5 [r5]++
//    gtrr 5 4 2 r[5] > r[4] ? -> r[2] = 1 | 0
//    addr 3 2 3 r[3]+r[2] -> r[3]
//    seti 2 0 3 r[2] -> [r3]
//
//    r2 = r1*r5
//    if(r2 == r4){
//        r2 = 1
//    }else{
//        r2 = 0
//    }
//    r5++
//    if(r5 > r4){
//        r2 = 1
//    }else{
//        r2 = 0
//    }
//    r3 = r2

}
