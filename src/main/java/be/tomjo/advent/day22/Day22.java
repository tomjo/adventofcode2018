package be.tomjo.advent.day22;

import be.tomjo.advent.Solution;
import be.tomjo.advent.math.V2;

import java.util.List;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Integer.parseInt;

public class Day22 implements Solution<Long, Integer, CaveSystem> {

    public static void main(String[] args) {
        new Day22().run();
    }

    @Override
    public CaveSystem parse(String input) {
        List<String> lines = lines(input);
        int depth = parseInt(lines.get(0).substring("depth: ".length()));
        String[] targetParts = lines.get(1).substring("target: ".length()).split(",");
        V2 target = new V2(parseInt(targetParts[0]), parseInt(targetParts[1]));
        return new CaveSystem(depth, target);
    }

    @Override
    public Long part1(CaveSystem caveSystem) {
        return caveSystem.getCaveRiskLevel();
    }

    @Override
    public Integer part2(CaveSystem caveSystem) {
        return new CaveExploration(caveSystem).findShortestStepsToTarget();
    }

}
