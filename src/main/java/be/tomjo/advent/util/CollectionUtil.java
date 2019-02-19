package be.tomjo.advent.util;

import be.tomjo.advent.day7.Edge;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.Opt;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Arrays.asList;

@UtilityClass
public class CollectionUtil {

    public static <T> Optional<T> last(List<T> list){
        return list == null || list.isEmpty() ? Optional.empty() : Optional.of(list.get(list.size()-1));
    }

    public static <T> void rotateDeque(Deque<T> deque, int n) {
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                T t = deque.removeLast();
                deque.addFirst(t);
            }
        } else {
            for (int i = 0; i < -n - 1; i++) {
                T t = deque.remove();
                deque.addLast(t);
            }
        }
    }

    public static <T> T onlyElementOf(Collection<T> collection){
        checkArgument(collection.size()==1);
        return collection.iterator().next();
    }

    public static <T> Set<T> of(Collection<T> ts, T... ts2){
        Set<T> set = new HashSet<>(ts);
        set.addAll(asList(ts2));
        return set;
    }

    public static <T> Set<T> merge(Collection<Set<T>> sets) {
        Set<T> merged = new HashSet<>();
        sets.forEach(merged::addAll);
        return merged;
    }

    public static <K,V> Map<K, V> unitMap(K key, V unitValue) {
        Map<K, V> map = new HashMap<>();
        map.put(key, unitValue);
        return map;
    }

    public static Map<Integer, Integer> mergeIntMapsByAddition(Map<Integer, Integer> x, Map<Integer, Integer> y) {
        y.forEach((key, value) -> x.merge(key, value, (a,b) -> a+b));
        return x;
    }


    public static <T> Predicate<T> not(Predicate<T> predicate){
        return predicate.negate();
    }

    public static BinaryOperator<Collection<Edge>> throwingMerger() {
        return (a, b) -> {
            throw new IllegalStateException();
        };
    }
}
