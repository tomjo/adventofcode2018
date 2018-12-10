package be.tomjo.advent.math;

import lombok.Value;

@Value
public class Rect {

    private final V2 min;
    private final V2 max;

    public boolean smallerThan(Rect rect) {
        return max.subtract(min).multiply() < rect.getMax().subtract(rect.getMin()).multiply();
    }
}
