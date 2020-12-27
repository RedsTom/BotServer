package org.redstom.botapi.events;

public interface IEvent {

    Class<? extends IEvent> getEventType();

}
