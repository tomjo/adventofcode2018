package be.tomjo.advent.day4;

import be.tomjo.advent.Solution;
import be.tomjo.advent.day4.event.GuardFellAsleep;
import be.tomjo.advent.day4.event.GuardShiftStarted;
import be.tomjo.advent.day4.event.GuardWokeUp;
import be.tomjo.advent.day4.readmodel.GuardWithMostMinutesAsleepAtMinuteReadModel;
import be.tomjo.advent.day4.readmodel.GuardWithMostMinutesAsleepReadModel;
import be.tomjo.advent.events.EventParser;
import com.google.common.collect.ImmutableMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.util.stream.Collectors.toList;

public class Day4 implements Solution<Integer, Integer, List<Object>> {

    private static final Pattern GUARD_ID_REGEX = Pattern.compile("^\\[(.*)\\] Guard #(\\d+) begins shift$");
    private static final Pattern FALLS_ASLEEP_REGEX = Pattern.compile("^\\[(.*)\\] falls asleep$");
    private static final Pattern WAKES_UP_REGEX = Pattern.compile("^\\[(.*)\\] wakes up$");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");

    public static void main(String[] args) {
        new Day4().run();
    }

    @Override
    public List<Object> parse(String input) {
        List<String> sortedInputs = lines(input).stream().sorted().collect(toList());
        EventParser eventParser = new EventParser(ImmutableMap.of(
                GUARD_ID_REGEX, Day4::createGuardShiftStarted,
                FALLS_ASLEEP_REGEX, Day4::createGuardFellAsleep,
                WAKES_UP_REGEX, Day4::createGuardWokeUp
        ));
        return eventParser.parseEvents(sortedInputs);
    }

    @Override
    public Integer part1(List<Object> events) {
        return new GuardWithMostMinutesAsleepReadModel()
                .applyEvents(events)
                .getMinuteWhichTheGuardWhoSleptTheMostSleptTheMostAt();
    }

    @Override
    public Integer part2(List<Object> events) {
        return new GuardWithMostMinutesAsleepAtMinuteReadModel()
                .applyEvents(events)
                .getMinuteMultipliedByGuardWhereHeWasMostAsleep();
    }

    private static Object createGuardShiftStarted(Matcher matcher) {
        String timestamp = matcher.toMatchResult().group(1);
        int guardId = Integer.parseInt(matcher.toMatchResult().group(2));
        return new GuardShiftStarted(guardId, parseTimestamp(timestamp));
    }

    private static Object createGuardFellAsleep(Matcher matcher) {
        String timestampAsleep = matcher.toMatchResult().group(1);
        return new GuardFellAsleep(parseTimestamp(timestampAsleep));
    }

    private static Object createGuardWokeUp(Matcher matcher) {
        String timestampWakeup = matcher.toMatchResult().group(1);
        return new GuardWokeUp(parseTimestamp(timestampWakeup));
    }

    private static LocalDateTime parseTimestamp(String timestamp) {
        return LocalDateTime.parse(timestamp, DATE_FORMATTER);
    }
}
