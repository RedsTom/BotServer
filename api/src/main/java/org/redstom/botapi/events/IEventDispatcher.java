package org.redstom.botapi.events;

public interface IEventDispatcher {

    <T extends IEvent> void dispatch(T event);

    void register(Class<?> eventSubscriberClass);

}
