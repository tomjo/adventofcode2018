package be.tomjo.advent;

import be.tomjo.advent.benchmark.BenchmarkResult;

import static be.tomjo.advent.benchmark.BenchmarkResult.benchmark;
import static be.tomjo.advent.util.InputUtil.readInput;

public interface Solution<RA,RB,P> {

    default int getDay(){
        return Integer.parseInt(getClass().getSimpleName().replace("Day", ""));
    }

    default String getInput(){
        return readInput(getDay()+".txt");
    }

    default P getPreprocessedInput(){
        return parse(getInput());
    }

    P parse(String input);

    RA part1(P input);
    RB part2(P input);

    default RA rawPart1(String input){
        return part1(parse(input));
    }

    default RB rawPart2(String input){
        return part2(parse(input));
    }

    default void run(){
        BenchmarkResult<P> preprocessed = benchmark(this::getPreprocessedInput);
        System.out.println("Solution " + getDay() + ".preprocessing | Took " + preprocessed.getTime() + " ms.");
        BenchmarkResult<RA> result1 = benchmark(() -> part1(preprocessed.getResult()));
        System.out.println("Solution " + getDay() + ".1: " + result1.getResult() + " | Took " + result1.getTime() + " ms.");
        BenchmarkResult<RB> result2 = benchmark(() -> part2(preprocessed.getResult()));
        System.out.println("Solution " + getDay() + ".2: " + result2.getResult() + " | Took " + result2.getTime() + " ms.");
    }
}
