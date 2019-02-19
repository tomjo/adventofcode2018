package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public interface GameMap {

    void initialize(int attackPower);

    Optional<Unit> getUnitAt(V2 location);

    boolean isObstructed(V2 location);

    List<Unit> getUnits();

    default Collection<Unit> getUnitsOfType(Collection<UnitType> types) {
        return getUnits().stream()
                .filter(u -> types.contains(u.getType()))
                .collect(toList());
    }

    Collection<V2> getLocationsInRange(V2 location);

    Collection<V2> getMoveableLocationsInRangeOfLocation(V2 location);

    int getWidth();

    int getHeight();


    void moveUnit(Unit unit, V2 location);

    void removeUnit(Unit unit);

    GameMap copy();
}
