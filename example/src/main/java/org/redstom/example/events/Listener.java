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
    public void onServerStarted(ServerStartedEvent event, @Inject("server") IServer server) {
        server.getLogger().info("The served has started ! (from Example plugin)");
    }

    @EventReceiver(MessageCreateEvent.class)
    public void onMessageCreate(MessageCreateEvent event) {
        event.getChannel().sendMessage("Message received !");
    }

}
