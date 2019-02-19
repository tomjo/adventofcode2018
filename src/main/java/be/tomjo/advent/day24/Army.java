package be.tomjo.advent.day24;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

@RequiredArgsConstructor
@Getter
@ToString
public class Army {

    private final Collection<UnitGroup> groups;

    public boolean contains(UnitGroup group) {
        return groups.contains(group);
    }

    public Map<UnitGroup, UnitGroup> findTargets(Army opponents) {
        Map<UnitGroup, UnitGroup> targets = new HashMap<>();
        List<UnitGroup> sorted = groups.stream().sorted(comparingInt(UnitGroup::getEffectivePower).reversed()
                .thenComparingInt(UnitGroup::getInitiative).reversed()).collect(Collectors.toList());
        sorted.stream().forEach(ug -> findBestOpponent(ug, opponents, targets));
        return targets;
    }

    private void findBestOpponent(UnitGroup ug, Army opponents, Map<UnitGroup, UnitGroup> targets) {
        Set<UnitGroup> potentialTargets = opponents.groups.stream()
                .filter(g -> !targets.values().contains(g))
                .filter(g -> !g.getImmunities().contains(ug.getAttackType()))
                .collect(Collectors.toSet());
        potentialTargets = compareGroups(potentialTargets, ug.getAttackType());
        Optional<UnitGroup> potentialTarget = potentialTargets.stream()
                .sorted(comparingInt(UnitGroup::getEffectivePower).reversed()
                        .thenComparingInt(UnitGroup::getInitiative).reversed())
                .findFirst();
        potentialTarget.ifPresent(pt -> targets.put(ug, pt));
    }
}
