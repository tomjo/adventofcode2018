package be.tomjo.advent.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtil {

    public static int mod(int a, int b){
        return ((a % b) + b) % b;
    }

}
