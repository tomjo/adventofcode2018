package be.tomjo.advent.day15;

public enum UnitType {
    ELF('E'), GOBLIN('G');

    private final char representation;

    UnitType(char representation) {
        this.representation = representation;
    }

    public char getRepresentation() {
        return representation;
    }
}
