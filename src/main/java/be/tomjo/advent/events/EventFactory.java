package be.tomjo.advent.events;

import java.util.regex.Matcher;

@FunctionalInterface
public interface EventFactory<T> {

    T create(Matcher matcher);
}
