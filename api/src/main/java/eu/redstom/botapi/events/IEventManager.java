package eu.redstom.botapi.events;

import eu.redstom.botapi.plugins.IPlugin;
import org.javacord.api.event.Event;

/**
 * The event dispatcher of the server
 */
public interface IEventManager {

    /**
     * Dispatch an event to all the plugins
     *
     * @param event Event to dispatch
     * @param <T>   Kind of event to dispatch
     */
    <T extends Event> void dispatch(T event);

    /**
     * Registers an event subscriber class
     *
     * @param eventSubscriberClass The class to register
     * @param plugin               The plugin that registers the event
     */
    void register(Object eventSubscriberClass, IPlugin plugin);

}
