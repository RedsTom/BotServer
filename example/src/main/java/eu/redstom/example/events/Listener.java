package eu.redstom.example.events;

import eu.redstom.botapi.events.EventBus;
import eu.redstom.botapi.events.EventReceiver;
import eu.redstom.botapi.events.SelfRegisteringListener;
import eu.redstom.botapi.events.types.ServerStartedEvent;
import eu.redstom.botapi.injector.Inject;
import eu.redstom.botapi.server.IServer;
import org.javacord.api.event.message.MessageCreateEvent;

@EventBus
@SelfRegisteringListener
public class Listener {

    @EventReceiver(ServerStartedEvent.class)
    public void onServerStarted(ServerStartedEvent event, @Inject("server") IServer server) {
        server.getLogger().info("The served has started ! (from Example plugin)");
    }

    @EventReceiver(MessageCreateEvent.class)
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageAuthor().isBotUser()) return;
        event.getChannel().sendMessage("Message received !");
    }

}
