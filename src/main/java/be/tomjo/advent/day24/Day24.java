package be.tomjo.advent.day24;

import be.tomjo.advent.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static be.tomjo.advent.util.StringUtils.lines;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Day24 implements Solution<Integer, Integer, ImmuneSystemSimulator> {

    private static final Pattern UNIT_GROUP_PATTERN = Pattern.compile("^(?<unitCount>.*) units each with (?<hitPoints>.*) " +
            "hit points (?<weaknessesAndImmunities>.*)with an attack that " +
            "does (?<damage>.*) (?<attackType>.*) damage at initiative (?<initiative>.*)$");

    public static void main(String[] args) {
        new Day24().run();
    }

    @Override
    public ImmuneSystemSimulator parse(String input) {
        List<String> lines = lines(input);
        List<Army> armies = new ArrayList<>();
        List<UnitGroup> currentUnitGroups = new ArrayList<>();
        for (String line : lines) {
            if (line.contains(":")) {
                currentUnitGroups = new ArrayList<>();
                armies.add(new Army(currentUnitGroups));
                continue;
            }
            Matcher matcher = UNIT_GROUP_PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalStateException();
            }
            UnitGroup currentGroup = new UnitGroup();
            currentUnitGroups.add(currentGroup);
            currentGroup.setUnits(parseInt(matcher.group("unitCount")));
            currentGroup.setHitPoints(parseInt(matcher.group("hitPoints")));
            currentGroup.setDamage(parseInt(matcher.group("damage")));
            currentGroup.setInitiative(parseInt(matcher.group("initiative")));
            currentGroup.setAttackType(DamageType.valueOf(matcher.group("attackType").toUpperCase()));
            String weaknessesAndImmunities = matcher.group("weaknessesAndImmunities");
            if (isNotBlank(weaknessesAndImmunities)) {
                String[] weaknessesAndImmunitiesParts = weaknessesAndImmunities.substring(1, weaknessesAndImmunities.length() - 2).split(";");
                for (String weaknessesAndImmunityPart : weaknessesAndImmunitiesParts) {
                    if (weaknessesAndImmunityPart.startsWith("weak")) {
                        String[] weaknesses = weaknessesAndImmunityPart.substring("weak to ".length()).split(", ");
                        currentGroup.setWeaknesses(Arrays.stream(weaknesses).map(String::toUpperCase).map(DamageType::valueOf).collect(toList()));
                    } else if (weaknessesAndImmunityPart.startsWith("immune")) {
                        String[] immunities = weaknessesAndImmunityPart.substring("immune to ".length()).split(", ");
                        currentGroup.setImmunities(Arrays.stream(immunities).map(String::toUpperCase).map(DamageType::valueOf).collect(toList()));
                    }
                }
            }
        }
        return new ImmuneSystemSimulator(armies.get(0), armies.get(1));
    }

    @Override
    public Integer part1(ImmuneSystemSimulator input) {
        input.fight();
        return null;
    }

    @Override
    public Integer part2(ImmuneSystemSimulator input) {
        return null;
    }

}
