package eu.redstom.botserver.events.executor;

import eu.redstom.botapi.events.EventPriority;
import eu.redstom.botapi.plugins.IPlugin;
import org.javacord.api.event.Event;

import java.lang.reflect.Method;

public class MethodEventExecutor<T extends Event> {

    private final IPlugin plugin;
    private Class<T> eventType;
    private Method method;
    private final Object classInstance;
    private final EventPriority priority;

    public MethodEventExecutor(Class<T> eventType, IPlugin plugin, Object classInstance, Method method, EventPriority priority) {
        this.classInstance = classInstance;
        this.plugin = plugin;
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

    public IPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public String toString() {
        return "MethodEventExecutor{" +
            "eventType=" + eventType +
            ", plugin=" + plugin +
            ", method=" + method +
            ", classInstance=" + classInstance +
            ", priority=" + priority +
            '}';
    }
}
