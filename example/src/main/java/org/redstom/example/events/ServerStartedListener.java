package org.redstom.example.events;

import org.redstom.botapi.events.EventBus;
import org.redstom.botapi.events.EventReceiver;
import org.redstom.botapi.events.types.MessageCreateEvent;
import org.redstom.botapi.events.types.ServerStartedEvent;

@EventBus
public class ServerStartedListener {

    @EventReceiver(ServerStartedEvent.class)
    public static void onServerStarted(ServerStartedEvent event) {
        System.out.println("Event called !");
    }

    @EventReceiver(MessageCreateEvent.class)
    public static void onMessageSent(MessageCreateEvent event) {
        if (event.getEvent().getMessageAuthor().isBotUser()) return;
        if (event.getEvent().getMessageAuthor().isBotUser()) return;

        event.getEvent().getChannel().sendMessage("Ok, received !");
    }

}
