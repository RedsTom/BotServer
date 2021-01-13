package org.redstom.example.events;

import org.javacord.api.event.message.MessageCreateEvent;
import org.redstom.botapi.events.EventBus;
import org.redstom.botapi.events.EventReceiver;
import org.redstom.botapi.events.SelfRegisteringListener;
import org.redstom.botapi.events.types.ServerStartedEvent;
import org.redstom.botapi.injector.Inject;
import org.redstom.botapi.server.IServer;

@EventBus
@SelfRegisteringListener
public class Listener {

    @EventReceiver(ServerStartedEvent.class)
    @Inject({IServer.class})
    public void onServerStarted(ServerStartedEvent event, IServer server) {
        System.out.println(server.getApi().getYourself().getName());
    }

    @EventReceiver(MessageCreateEvent.class)
    public void onMessageSent(MessageCreateEvent event) {
        if (event.getMessageAuthor().isBotUser()) return;

        event.getChannel().sendMessage("Ok, received !");
    }

}
