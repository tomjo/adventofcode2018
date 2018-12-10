package be.tomjo.advent.events;

import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface ReadModel<T extends ReadModel> {

    Map<String, Method> eventHandlerCache = new HashMap<>();

    @SneakyThrows
    default T applyEvent(Object event) {
        eventHandlerCache.computeIfAbsent(getClass().getName() + "#" + event.getClass().getName(), key -> getEventHandlerMethod(event))
                .invoke(this, event);
        return (T) this;
    }

    default Method getEventHandlerMethod(Object event) {
        try {
            Method handlerMethod = getClass().getDeclaredMethod("on", event.getClass());
            handlerMethod.setAccessible(true);
            return handlerMethod;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    default T applyEvents(Collection<Object> events) {
        events.forEach(this::applyEvent);
        return (T) this;
    }
}
