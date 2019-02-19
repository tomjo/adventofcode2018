package be.tomjo.advent.day15;

import java.util.Comparator;

public class UnitTurnOrderComparator implements Comparator<Unit> {

    private static final LocationComparator LOCATION_COMPARATOR = new LocationComparator();

    @Override
    public int compare(Unit o1, Unit o2) {
        return LOCATION_COMPARATOR.compare(o1.getLocation(), o2.getLocation());
    }
}
