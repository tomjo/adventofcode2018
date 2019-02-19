package be.tomjo.advent.math;

import lombok.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Value
public class V2 {
    private final long x, y;

    public V2 add(V2 v) {
        return new V2(x + v.x, y + v.y);
    }

    public V2 subtract(V2 v) {
        return new V2(x - v.x, y - v.y);
    }

    public long multiply() {
        return x * y;
    }

    public V2 multiply(long n) {
        return new V2(x * n, y * n);
    }

    public int getXAsInt() {
        return (int) x;
    }

    public int getYAsInt() {
        return (int) y;
    }

    public V2 flip() {
        return new V2(y, x);
    }

    public V2 withOffsetY(int y) {
        return new V2(this.x, this.y + y);
    }

    public V2 withOffsetX(int x) {
        return new V2(this.x + x, this.y);
    }

    public int[] toArray() {
        return new int[]{getXAsInt(), getYAsInt()};
    }

    public Collection<V2> getVerticalAndHorizontalNeighbours() {
        List<V2> neighbours = new ArrayList<>();
        neighbours.add(new V2(getXAsInt() - 1, getYAsInt()));
        neighbours.add(new V2(getXAsInt() + 1, getYAsInt()));
        neighbours.add(new V2(getXAsInt(), getYAsInt() - 1));
        neighbours.add(new V2(getXAsInt(), getYAsInt() + 1));
        return neighbours;
    }
}
