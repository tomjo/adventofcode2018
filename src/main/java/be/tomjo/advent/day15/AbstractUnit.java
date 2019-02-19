package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;

public abstract class AbstractUnit implements Unit {

    private final UnitType type;
    private int hitPoints;
    private int attackPower;
    private V2 location;

    protected AbstractUnit(UnitType type, int hitPoints, int attackPower, V2 location) {
        this.type = type;
        this.hitPoints = hitPoints;
        this.attackPower = attackPower;
        this.location = location;
    }

    @Override
    public void move(V2 location) {
        this.location = location;
    }

    @Override
    public void damage(int attackPower) {
        this.hitPoints -= attackPower;
    }

    @Override
    public UnitType getType() {
        return type;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public V2 getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return getType()+" @ "+getLocation().getXAsInt()+","+getLocation().getYAsInt()+" [HP: "+getHitPoints()+"]";
    }
}
