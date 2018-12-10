package be.tomjo.advent.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventParser {

    private final Map<Pattern,EventFactory> eventFactoryMap;

    public EventParser(Map<Pattern, EventFactory> eventFactoryMap) {
        this.eventFactoryMap = eventFactoryMap;
    }

    public List<Object> parseEvents(List<String> inputs){
        List<Object> events = new ArrayList<>();
        for (String input : inputs) {
            for (Map.Entry<Pattern, EventFactory> factoryEntry : eventFactoryMap.entrySet()) {
                Matcher matcher = factoryEntry.getKey().matcher(input);
                if(matcher.find()){
                    events.add(factoryEntry.getValue().create(matcher));
                }
            }
        }
        return events;
    }
}
