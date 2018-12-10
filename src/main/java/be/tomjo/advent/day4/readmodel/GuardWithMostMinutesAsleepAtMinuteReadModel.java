package be.tomjo.advent.day4.readmodel;

import be.tomjo.advent.day4.event.GuardFellAsleep;
import be.tomjo.advent.day4.event.GuardShiftStarted;
import be.tomjo.advent.day4.event.GuardWokeUp;
import be.tomjo.advent.events.ReadModel;
import be.tomjo.advent.util.CollectionUtil;
import be.tomjo.advent.util.InputUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static be.tomjo.advent.util.CollectionUtil.unitMap;

public class GuardWithMostMinutesAsleepAtMinuteReadModel implements ReadModel<GuardWithMostMinutesAsleepAtMinuteReadModel> {
    private Map<Integer, Map<Integer, Integer>> guardSleepMinutesAtMinute = new HashMap<>();

    private int currentStart;
    private int currentGuardId;

    public void on(GuardShiftStarted event) {
        this.currentGuardId = event.getGuardId();
    }

    public void on(GuardFellAsleep event) {
        currentStart = event.getTimestamp().getMinute();
    }

    public void on(GuardWokeUp event) {
        IntStream.range(currentStart, event.getTimestamp().getMinute())
                .forEach(minute -> guardSleepMinutesAtMinute.merge(minute, unitMap(currentGuardId, 1), CollectionUtil::mergeIntMapsByAddition));
    }

    public int getMinuteMultipliedByGuardWhereHeWasMostAsleep() {
        int mostAsleep = 0;
        int minute = 0;
        int guard = 0;
        for (Map.Entry<Integer, Map<Integer, Integer>> minuteGuardMap : guardSleepMinutesAtMinute.entrySet()) {
            for (Map.Entry<Integer, Integer> guardFrequencyMap : minuteGuardMap.getValue().entrySet()) {
                if (guardFrequencyMap.getValue() > mostAsleep) {
                    mostAsleep = guardFrequencyMap.getValue();
                    minute = minuteGuardMap.getKey();
                    guard = guardFrequencyMap.getKey();
                }
            }
        }
        return minute * guard;
    }
}
