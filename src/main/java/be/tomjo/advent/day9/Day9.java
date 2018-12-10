package be.tomjo.advent.day9;

import be.tomjo.advent.Solution;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Integer.parseInt;

public class Day9 implements Solution<Long, Long, MarbleGameInput> {

    private static final Pattern INPUT_REGEX = Pattern.compile("^(.+) players; last marble is worth (.+) points$");

    public static void main(String[] args) {
        new Day9().run();
    }

    @Override
    public MarbleGameInput parse(String input) {
        Matcher matcher = INPUT_REGEX.matcher(lines(input).get(0));
        if (!matcher.find()) {
            throw new IllegalStateException();
        }
        int playerCount = parseInt(matcher.group(1));
        int marbleCount = parseInt(matcher.group(2));
        return new MarbleGameInput(playerCount, marbleCount, 23, -7);
    }

    @Override
    public Long part1(MarbleGameInput input) {
        return playGame(input, 1, MarbleCircle::new);
    }

    @Override
    public Long part2(MarbleGameInput input) {
        return playGame(input, 100, MarbleCircle::new);
    }

    private Long playGame(MarbleGameInput input, int highestMarbleValueModifier, Supplier<MarbleCircle> marbleCircleSupplier) {
        MarbleGame game = new MarbleGame(input, marbleCircleSupplier.get(), highestMarbleValueModifier);
        while(!game.isFinished()){
            game.playRound();
        }
        return game.getHighestScore();
    }

}
