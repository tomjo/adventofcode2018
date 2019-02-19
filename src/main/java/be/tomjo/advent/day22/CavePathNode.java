package be.tomjo.advent.day22;

import be.tomjo.advent.math.V2;
import lombok.Value;

@Value
public class CavePathNode {
    private final V2 location;
    private final Tool tool;
}
