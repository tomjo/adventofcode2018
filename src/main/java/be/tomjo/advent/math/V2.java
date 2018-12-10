package be.tomjo.advent.math;

import lombok.Value;

@Value
public class V2 {
    private final long x,y;

    public V2 add(V2 v) {
        return new V2(x+v.x, y+v.y);
    }

    public V2 subtract(V2 v) {
        return new V2(x-v.x, y-v.y);
    }

    public long multiply() {
        return x*y;
    }

    public V2 multiply(long n) {
        return new V2(x*n, y*n);
    }

    public int getXAsInt(){
        return (int) x;
    }

    public int getYAsInt(){
        return (int) y;
    }
}
