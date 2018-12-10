package be.tomjo.advent.day9;

import lombok.Value;

@Value
class MarbleGameInput {
    private final int playerCount;
    private final int highestMarbleValue;
    private final int magicMarbleValue;
    private final int magicMarbleRemovalOffset;
}
