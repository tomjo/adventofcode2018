package be.tomjo.advent.day15;

import be.tomjo.advent.math.V2;

import java.util.Comparator;

public class LocationComparator implements Comparator<V2> {
    @Override
    public int compare(V2 o1, V2 o2) {
        return Comparator.comparingInt(V2::getYAsInt)
                .thenComparingInt(V2::getXAsInt)
                .compare(o1, o2);
    }
}
