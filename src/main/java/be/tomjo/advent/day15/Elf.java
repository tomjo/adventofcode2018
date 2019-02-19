package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;

import java.util.Collection;
import java.util.Collections;

public class Elf extends AbstractUnit {

    private static final int DEFAULT_HIT_POINTS = 200;

    public Elf(V2 location, int attackPower) {
        super(UnitType.ELF, DEFAULT_HIT_POINTS, attackPower, location);
    }

    @Override
    public Collection<UnitType> getAttackableTypes() {
        return Collections.singletonList(UnitType.GOBLIN);
    }
}
