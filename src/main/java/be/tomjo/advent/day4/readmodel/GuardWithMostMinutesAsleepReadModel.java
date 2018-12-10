package be.tomjo.advent.day4.readmodel;
import be.tomjo.advent.util.CollectionUtil;
import be.tomjo.advent.util.InputUtil;
import be.tomjo.advent.day4.event.GuardFellAsleep;
import be.tomjo.advent.day4.event.GuardShiftStarted;
import be.tomjo.advent.day4.event.GuardWokeUp;
import be.tomjo.advent.events.ReadModel;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static be.tomjo.advent.util.CollectionUtil.unitMap;
import static java.util.stream.IntStream.range;

public class GuardWithMostMinutesAsleepReadModel implements ReadModel<GuardWithMostMinutesAsleepReadModel> {

    private final Map<Integer, Map<Integer, Integer>> sleepMinuteFrequencies = new HashMap<>();

    private int currentSleepStartMinute;
    private int currentGuardId;

    public void on(GuardShiftStarted event) {
        this.currentGuardId = event.getGuardId();
    }

    public void on(GuardFellAsleep event) {
        currentSleepStartMinute = event.getTimestamp().getMinute();
    }

    public void on(GuardWokeUp event) {
        range(currentSleepStartMinute, event.getTimestamp().getMinute())
                .forEach(minute -> sleepMinuteFrequencies.merge(currentGuardId, unitMap(minute, 1), CollectionUtil::mergeIntMapsByAddition));
    }

    public int getMinuteWhichTheGuardWhoSleptTheMostSleptTheMostAt() {
        int guardId = getGuardIdWithMostMinutesAsleep();
        int minute = getMinuteAtWhichGuardSleptMost(guardId);
        return guardId*minute;
    }

    public int getMinuteAtWhichGuardSleptMost(int guardId) {
        return sleepMinuteFrequencies.get(guardId).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalStateException::new);
    }

    public int getGuardIdWithMostMinutesAsleep() {
        return sleepMinuteFrequencies.entrySet()
                .stream()
                .max(Comparator.comparingInt(this::getTotalSumGuardAsleep))
                .map(Map.Entry::getKey)
                .orElseThrow(IllegalStateException::new);
    }

    public int getTotalSumGuardAsleep(Map.Entry<Integer, Map<Integer, Integer>> minuteFrequencyMapForGuard) {
        return minuteFrequencyMapForGuard.getValue().values().stream().mapToInt(i -> i).sum();
    }
}
