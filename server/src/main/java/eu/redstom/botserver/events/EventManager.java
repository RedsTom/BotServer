package eu.redstom.botserver.events;

import eu.redstom.botapi.events.EventBus;
import eu.redstom.botapi.events.EventPriority;
import eu.redstom.botapi.events.EventReceiver;
import eu.redstom.botapi.events.IEventManager;
import eu.redstom.botapi.plugins.IPlugin;
import eu.redstom.botserver.events.executor.MethodEventExecutor;
import eu.redstom.botserver.plugins.loader.exceptions.MissingAnnotationException;
import eu.redstom.botserver.plugins.loader.wirer.ParametersWirer;
import org.javacord.api.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventManager implements IEventManager {

    private final Set<MethodEventExecutor<?>> events;
    private final Map<String, Object> availableParams;

    public EventManager(Map<String, Object> availableParams) {
        this.availableParams = availableParams;
        this.events = new HashSet<>();
    }

    public <T extends Event> void dispatch(T event) {
        for (MethodEventExecutor<?> methodEventExecutor : events.stream()
            .filter(a -> a.getEventType().isAssignableFrom(event.getClass()))
            .sorted((a, b) -> a.getPriority().getPriority() > b.getPriority().getPriority() ? -1 : 1)
            .toArray(MethodEventExecutor<?>[]::new)) {
            singleEventDispatch(methodEventExecutor, event);
        }
    }

    private <T extends Event> void singleEventDispatch(MethodEventExecutor<?> methodEventExecutor, T event) {
        try {
            availableParams.put("plugin", methodEventExecutor.getPlugin());
            ParametersWirer<?> wirer = new ParametersWirer<>(
                methodEventExecutor.getClassInstance().getClass());
            wirer.invoke(
                methodEventExecutor.getMethod(),
                methodEventExecutor.getClassInstance(),
                new Object[]{event},
                availableParams
            );
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Cannot invoke method "
                + methodEventExecutor.getMethod().getName()
                + "\n in class "
                + methodEventExecutor.getClassInstance().getClass().getName()
                + "\n whilst calling event "
                + event.getClass().getSimpleName());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(Object classInstance, IPlugin plugin) {
        Class<?> eventSubscriberClass = classInstance.getClass();
        if (!(eventSubscriberClass.isAnnotationPresent(EventBus.class))) {
            throw new MissingAnnotationException("Cannot find the EventBus annotation on the target class !");
        }
        Arrays.stream(eventSubscriberClass.getMethods()).filter(a -> a.isAnnotationPresent(EventReceiver.class))
            .forEach(method -> {
                EventReceiver receiver = method.getAnnotation(EventReceiver.class);
                addEventMethod(receiver.value(), plugin, classInstance, method, receiver.priority());
            });
    }

    private void addEventMethod(
        Class<? extends Event> type, IPlugin plugin, Object classInstance, Method method, EventPriority priority) {
        events.add(new MethodEventExecutor<>(type, plugin, classInstance, method, priority));
    }
}
