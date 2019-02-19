package be.tomjo.advent.day13;

import be.tomjo.advent.math.V2;
import be.tomjo.advent.util.MathUtil;

import static java.util.Arrays.stream;

public enum Direction {
    NORTH('^', new V2(0, -1)),
    EAST('>', new V2(1, 0)),
    SOUTH('v', new V2(0, 1)),
    WEST('<', new V2(-1, 0));

    private final char representation;
    private final V2 rawVector;

    Direction(char representation, V2 rawVector) {
        this.representation = representation;
        this.rawVector = rawVector;
    }

    public static Direction of(char representation) {
        return stream(Direction.values())
                .filter(d -> d.representation == representation)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public V2 getRawVector() {
        return rawVector;
    }

    public Direction turnCW() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Direction turnCCW() {
        return values()[MathUtil.mod(ordinal() - 1, values().length)];
    }

    public Direction turn(char track) {
        if (this == NORTH || this == SOUTH) {
            if (track == '\\') {
                return turnCCW();
            }
            return turnCW();
        }
        if (track == '\\') {
            return turnCW();
        }
        return turnCCW();
    }

    public Direction turn(TurnState turnState) {
        switch (turnState) {
            case LEFT:
                return turnCCW();
            case RIGHT:
                return turnCW();
            case STRAIGHT:
                return this;
        }
        throw new IllegalStateException();
    }
}
