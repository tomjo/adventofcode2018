package be.tomjo.advent.day13;

public enum TurnState {
    LEFT, STRAIGHT, RIGHT;

    TurnState nextTurnState() {
        return TurnState.values()[(ordinal() + 1) % TurnState.values().length];
    }

}
