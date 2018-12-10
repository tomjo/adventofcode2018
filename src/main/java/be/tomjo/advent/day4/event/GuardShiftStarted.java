package be.tomjo.advent.day4.event;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class GuardShiftStarted {
    private final int guardId;
    private final LocalDateTime timestamp;
}
