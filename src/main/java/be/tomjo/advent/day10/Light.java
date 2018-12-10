package be.tomjo.advent.day10;

import be.tomjo.advent.math.V2;
import lombok.Value;

@Value
public class Light {
    private final V2 position;
    private final V2 velocity;

    public Light iterate(int n) {
        return new Light(position.add(velocity.multiply(n)), velocity);
    }
}
