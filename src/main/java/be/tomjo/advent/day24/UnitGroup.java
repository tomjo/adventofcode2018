package be.tomjo.advent.day24;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class UnitGroup {
    @EqualsAndHashCode.Include
    private final UUID id;
    private int hitPoints;
    private int units;
    private int damage;
    private DamageType attackType;
    private Collection<DamageType> weaknesses = new ArrayList<>();
    private Collection<DamageType> immunities = new ArrayList<>();
    private int initiative;

    public UnitGroup() {
        this.id = UUID.randomUUID();
    }

    public int getEffectivePower() {
        return damage * units;
    }

    public int getTotalHitPoints(){
        return hitPoints*units;
    }

    public int getEffectivePowerAgainst(UnitGroup unitGroup) {
        if (unitGroup.getImmunities().contains(attackType)) {
            return 0;
        }
        if (unitGroup.getWeaknesses().contains(attackType)) {
            return 2 * getEffectivePower();
        }
        return getEffectivePower();
    }

    public void removeUnits(int unitsLost) {
        this.units -= unitsLost;
    }
}
