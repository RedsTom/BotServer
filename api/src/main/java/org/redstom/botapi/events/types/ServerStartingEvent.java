package org.redstom.botapi.events.types;

import org.redstom.botapi.events.IEvent;

public class ServerStartingEvent implements IEvent {
    @Override
    public Class<? extends IEvent> getEventType() {
        return this.getClass();
    }
}
