package be.tomjo.advent.day9;

import be.tomjo.advent.util.CollectionUtil;

import java.util.*;

public class MarbleCircle {

    private final Deque<Integer> marbles;

    public MarbleCircle() {
        this.marbles = new ArrayDeque<>();
        this.marbles.add(0);
    }

    public void addMarble(int marble) {
        CollectionUtil.rotateDeque(marbles, 2);
        marbles.addLast(marble);
    }

    public int removeMarbleAtOffsetOfCurrentMarble(int offset) {
        CollectionUtil.rotateDeque(marbles, offset);
        return marbles.pop();
    }

}
