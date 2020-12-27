package org.redstom.botapi.events.types;

import org.redstom.botapi.events.IEvent;

public class MessageCreateEvent implements IEvent {

    private final org.javacord.api.event.message.MessageCreateEvent event;

    public MessageCreateEvent(org.javacord.api.event.message.MessageCreateEvent event) {
        this.event = event;
    }

    public org.javacord.api.event.message.MessageCreateEvent getEvent() {
        return event;
    }

    @Override
    public Class<? extends IEvent> getEventType() {
        return getClass();
    }


}
