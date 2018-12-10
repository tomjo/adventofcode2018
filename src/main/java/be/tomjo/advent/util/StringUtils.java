package be.tomjo.advent.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static be.tomjo.advent.util.CollectionUtil.not;

@UtilityClass
public class StringUtils {
    public static List<String> lines(String s){
        return Arrays.stream(s.replace("\r", "").split("\n"))
                .filter(Objects::nonNull)
                .filter(not(String::isEmpty))
                .collect(Collectors.toList());
    }

    public static String stringWithoutCharAt(String s, int position) {
        return new StringBuilder(s).deleteCharAt(position).toString();
    }
}
