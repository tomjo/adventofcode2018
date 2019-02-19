package be.tomjo.advent.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class ArrayUtils {

    public static int[] concat(int[] first, int[] second) {
        int[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static int indexOf(int[] array, int value){
        for (int i = 0; i < array.length; i++) {
            if(array[i] == value){
                return i;
            }
        }
        return -1;
    }

    public static <T> int indexOf(T[] array, T value){
        for (int i = 0; i < array.length; i++) {
            if(Objects.equals(array[i], value)){
                return i;
            }
        }
        return -1;
    }

    public static int sum(int[] ints){
        int sum = 0;
        for (int i : ints) {
            sum += i;
        }
        return sum;
    }

    public static int[] copy(int[] array) {
        int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }
}
