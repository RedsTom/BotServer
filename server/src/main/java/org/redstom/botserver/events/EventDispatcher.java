package org.redstom.botserver.events;

import org.redstom.botapi.events.EventBus;
import org.redstom.botapi.events.EventReceiver;
import org.redstom.botapi.events.IEvent;
import org.redstom.botapi.events.IEventDispatcher;
import org.redstom.botserver.java.Maps;
import org.redstom.botserver.java.exceptions.MissingAnnotationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventDispatcher implements IEventDispatcher {

    private final Map<Class<? extends IEvent>, ArrayList<Method>> events;

    public EventDispatcher() {
        this.events = new HashMap<>();
    }

    public <T extends IEvent> void dispatch(T event) {
        events.putIfAbsent(event.getClass(), new ArrayList<>());
        Maps.getIfPresent(events, event.getEventType(), (methods) -> methods.forEach((method) -> {
            try {
                method.invoke(this, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }));
    }

    @Override
    public void register(Class<?> eventSubscriberClass) {
        EventBus eventBus = eventSubscriberClass.getAnnotation(EventBus.class);
        if (eventBus == null) {
            throw new MissingAnnotationException("Cannot find the EventBus annotation on the target class !");
        }

        for (Method method : eventSubscriberClass.getMethods()) {
            EventReceiver receiver = method.getAnnotation(EventReceiver.class);
            if (receiver == null) continue;
            addEventMethod(receiver.value(), method);
        }
    }

    private void addEventMethod(Class<? extends IEvent> type, Method method) {
        events.putIfAbsent(type, new ArrayList<>());
        events.get(type).add(method);
    }
}
