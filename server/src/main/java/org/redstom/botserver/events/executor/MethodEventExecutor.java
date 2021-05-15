package org.redstom.botserver.events.executor;

import org.javacord.api.event.Event;
import org.redstom.botapi.events.EventPriority;

import java.lang.reflect.Method;

public class MethodEventExecutor<T extends Event> {

    private Class<T> eventType;
    private Method method;
    private final Object classInstance;
    private final EventPriority priority;

    public MethodEventExecutor(Class<T> eventType, Object classInstance, Method method, EventPriority priority) {
        this.classInstance = classInstance;
        this.eventType = eventType;
        this.method = method;
        this.priority = priority;
    }

    public Object getClassInstance() {
        return classInstance;
    }

    public Class<? extends Event> getEventType() {
        return eventType;
    }

    public void setEventType(Class<T> eventType) {
        this.eventType = eventType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public EventPriority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "MethodEventExecutor{" +
            "eventType=" + eventType +
            ", method=" + method +
            ", classInstance=" + classInstance +
            ", priority=" + priority +
            '}';
    }
}
