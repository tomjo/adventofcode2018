package be.tomjo.advent.day8;

import be.tomjo.advent.Solution;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.util.Arrays.stream;

public class Day8 implements Solution<Integer, Integer, LicenseFileNode> {

    public static void main(String[] args) {
        new Day8().run();
    }

    @Override
    public LicenseFileNode parse(String input) {
        int[] licenseFile = stream(lines(input).get(0).split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        LicenseFileParser licenseFileParser = new LicenseFileParser(licenseFile);
        return licenseFileParser.parseLicenseFileNode();
    }

    @Override
    public Integer part1(LicenseFileNode input) {
        return input.getMetadataSum();
    }

    @Override
    public Integer part2(LicenseFileNode input) {
        return input.getValue();
    }
}
