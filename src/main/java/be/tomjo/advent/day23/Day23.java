package be.tomjo.advent.day23;

import be.tomjo.advent.Solution;
import be.tomjo.advent.math.V3;
import lombok.Value;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.*;

public class Day23 implements Solution<Integer, Long, Collection<Day23.NanoBot>> {

    private static final Pattern NANO_BOT_REGEX = Pattern.compile("^pos=<(.*),(.*),(.*)>, r=(.*)$");


    public static void main(String[] args) {
        new Day23().run();
    }

    @Override
    public Collection<Day23.NanoBot> parse(String input) {
        return lines(input).stream()
                .map(this::toNanoBot)
                .collect(toList());
    }

    private NanoBot toNanoBot(String line) {
        Matcher matcher = NANO_BOT_REGEX.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        return new NanoBot(new V3(parseLong(matcher.group(1)), parseLong(matcher.group(2)), parseLong(matcher.group(3))), parseLong(matcher.group(4)));
    }

    @Override
    public Integer part1(Collection<Day23.NanoBot> input) {
        NanoBot nanoBotWithLargestRadius = input.stream()
                .max(Comparator.comparingLong(NanoBot::getRadius))
                .orElseThrow(IllegalStateException::new);
        return (int) input.stream()
                .filter(n -> manhattanDistanceToPoint(nanoBotWithLargestRadius.getLocation(), n.getLocation()) <= nanoBotWithLargestRadius.getRadius())
                .count();
    }

    public int getNanoBotCountInRange(Collection<NanoBot> nanoBots, V3 location) {
        return (int) nanoBots.stream()
                .filter(n -> manhattanDistanceToPoint(location, n.getLocation()) <= n.getRadius())
                .count();
    }

    private static long manhattanDistanceToPoint(V3 p, V3 q) {
        return (Math.abs(p.getX() - q.getX()) + Math.abs(p.getY() - q.getY()) + Math.abs(p.getZ() - q.getZ()));
    }

    @Override
    public Long part2(Collection<Day23.NanoBot> input) {

        for (NanoBot nanoBot : input) {
            List<NanoBot> inRange = input.stream()
                    .filter(n -> manhattanDistanceToPoint(nanoBot.getLocation(), n.getLocation()) <= n.getRadius())
                    .collect(toList());
            System.out.println(nanoBot+" | "+inRange);
        }
        long minX = input.stream()
                .mapToLong(n -> n.getLocation().getX()-n.getRadius())
                .min()
                .orElseThrow(IllegalStateException::new);
        long maxX = input.stream()
                .mapToLong(n -> n.getLocation().getX()+n.getRadius())
                .max()
                .orElseThrow(IllegalStateException::new);
        long minY = input.stream()
                .mapToLong(n -> n.getLocation().getY()-n.getRadius())
                .min()
                .orElseThrow(IllegalStateException::new);
        long maxY = input.stream()
                .mapToLong(n -> n.getLocation().getY()+n.getRadius())
                .max()
                .orElseThrow(IllegalStateException::new);
        long minZ = input.stream()
                .mapToLong(n -> n.getLocation().getZ()-n.getRadius())
                .min()
                .orElseThrow(IllegalStateException::new);
        long maxZ = input.stream()
                .mapToLong(n -> n.getLocation().getZ()+n.getRadius())
                .max()
                .orElseThrow(IllegalStateException::new);
        System.out.println(new V3(minX, minY, minZ)+" "+new V3(maxX, maxY, maxZ));


        Map<NanoBot, Integer> map = input.stream()
                .collect(toMap(Function.identity(), n -> getNanoBotCountInRange(input, n.getLocation())));
        NanoBot nanoBotInRangeOfMost = map.entrySet()
                .stream()
                .max(Comparator.<Map.Entry<NanoBot,Integer>>comparingInt(Map.Entry::getValue)
                        .thenComparing(Comparator.<Map.Entry<NanoBot,Integer>>comparingLong(n -> manhattanDistanceToPoint(new V3(0,0,0), n.getKey().getLocation()))))
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalStateException::new);
        System.out.println(nanoBotInRangeOfMost);
        return manhattanDistanceToPoint(new V3(0, 0,0 ), nanoBotInRangeOfMost.getLocation());
    }

    @Value
    static class NanoBot {
        private final V3 location;
        private final long radius;
    }

}
