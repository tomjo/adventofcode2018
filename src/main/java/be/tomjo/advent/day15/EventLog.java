package be.tomjo.advent.day15;

import java.util.ArrayList;
import java.util.List;

public class EventLog {

    private static EventLog currentInstance;

    private final List<Object> events;

    public EventLog() {
        events = new ArrayList<>();
    }

    public void addEvent(Object event){
        events.add(event);
    }

    public static void publishEvent(Object event){
        if(currentInstance == null){
            currentInstance = new EventLog();
        }
        currentInstance.addEvent(event);
    }

    public static void printEvents(){
        for (Object event : currentInstance.events) {
            System.out.println(event);
        }
    }

    @Override
    public String toString() {
        return events.toString();
    }
}
