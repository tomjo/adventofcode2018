package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;

import java.util.Collection;

public interface Unit {

    UnitType getType();
    int getHitPoints();
    int getAttackPower();
    V2 getLocation();
    Collection<UnitType> getAttackableTypes();

    void move(V2 v2);

    void damage(int attackPower);

    default boolean isDead(){
        return getHitPoints() <= 0;
    }
}
