package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;

import java.util.Collection;
import java.util.Collections;

public class Goblin extends AbstractUnit {

    private static final int DEFAULT_HIT_POINTS = 200;
    private static final int DEFAULT_ATTACK_POWER = 3;

    public Goblin(V2 location) {
        super(UnitType.GOBLIN, DEFAULT_HIT_POINTS, DEFAULT_ATTACK_POWER, location);
    }


    @Override
    public Collection<UnitType> getAttackableTypes() {
        return Collections.singletonList(UnitType.ELF);
    }
}
