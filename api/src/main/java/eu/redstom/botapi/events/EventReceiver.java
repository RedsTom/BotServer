package eu.redstom.botapi.events;

import org.javacord.api.event.Event;

import java.lang.annotation.*;

/**
 * Declares a method as an event receiver
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventReceiver {

    /**
     * @return The type of event to register
     */
    Class<? extends Event> value();

    /**
     * @return The priority of the event
     */
    EventPriority priority() default EventPriority.NORMAL;
}
