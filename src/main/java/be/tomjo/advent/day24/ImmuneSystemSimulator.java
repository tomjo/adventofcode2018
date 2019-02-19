package be.tomjo.advent.day24;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

@RequiredArgsConstructor
@ToString
public class ImmuneSystemSimulator {

    private final Army immuneSystem;
    private final Army infection;


    public void fight() {
        Map<UnitGroup, UnitGroup> targetMapping = selectTargets();
        attack(targetMapping);
        System.out.println(this);
    }


    public void attack(Map<UnitGroup, UnitGroup> targetMapping){
        List<UnitGroup> groups = new ArrayList<>(immuneSystem.getGroups());
        groups.addAll(infection.getGroups());
        groups.sort(comparingInt(UnitGroup::getInitiative).reversed());
        for (UnitGroup attackingGroup : groups) {
            UnitGroup defendingGroup = targetMapping.get(attackingGroup);
            int power = attackingGroup.getEffectivePowerAgainst(defendingGroup);
            int unitsLost = power / attackingGroup.getHitPoints();
            System.out.println(attackingGroup+" > "+defendingGroup+": units lost: "+unitsLost);
            defendingGroup.removeUnits(unitsLost);
        }
    }

    private Map<UnitGroup,UnitGroup> selectTargets() {
        List<UnitGroup> groups = new ArrayList<>(immuneSystem.getGroups());
        groups.addAll(infection.getGroups());
        groups.sort(comparingInt(UnitGroup::getEffectivePower)
                .thenComparingInt(UnitGroup::getInitiative));
        Map<UnitGroup,UnitGroup> targetMapping = new HashMap<>();
        for (UnitGroup group : groups) {
            //choose target
            List<UnitGroup> enemyGroups = getEnemyUnitGroups(group, targetMapping);
            Optional<UnitGroup> targetGroup = enemyGroups.stream()
                    .min(comparingInt(group::getEffectivePowerAgainst).reversed()
                            .thenComparingInt(UnitGroup::getEffectivePower).reversed()
                            .thenComparingInt(UnitGroup::getInitiative).reversed())
                    .filter(u -> group.getEffectivePowerAgainst(u) > 0);
            targetMapping.put(group, targetGroup.orElse(null));
        }
        return targetMapping;
    }

    private List<UnitGroup> getEnemyUnitGroups(UnitGroup group, Map<UnitGroup, UnitGroup> targetMapping) {
        if (immuneSystem.contains(group)) {
            ArrayList<UnitGroup> enemyGroups = new ArrayList<>(infection.getGroups());
            enemyGroups.removeAll(targetMapping.values());
            return enemyGroups;
        }
        ArrayList<UnitGroup> enemyGroups = new ArrayList<>(immuneSystem.getGroups());
        enemyGroups.removeAll(targetMapping.values());
        return enemyGroups;
    }
}
