package be.tomjo.advent.day4.event;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class GuardFellAsleep {
    private final LocalDateTime timestamp;
}
