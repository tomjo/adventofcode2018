package be.tomjo.advent.day3;

import be.tomjo.advent.Solution;

import java.util.List;

import static be.tomjo.advent.util.StringUtils.lines;
import static be.tomjo.advent.util.CollectionUtil.onlyElementOf;
import static java.util.stream.Collectors.toList;

public class Day3 implements Solution<Integer, Integer, ClaimGrid> {

    public static void main(String[] args) {
        new Day3().run();
    }

    @Override
    public Integer part1(ClaimGrid claimGrid) {
        return claimGrid.calculateAreaInMoreThanOneClaim();
    }

    @Override
    public Integer part2(ClaimGrid claimGrid) {
        return onlyElementOf(claimGrid.getNonOverlappingClaims());
    }

    @Override
    public ClaimGrid parse(String input) {
        List<Claim> claims = inputToClaims(input);
        return new ClaimGrid(1000, 1000, claims);
    }

    private static List<Claim> inputToClaims(String input) {
        return lines(input).stream()
                .map(Claim::new)
                .collect(toList());
    }
}
