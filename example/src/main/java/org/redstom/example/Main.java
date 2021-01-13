package org.redstom.example;

import org.redstom.botapi.BotPlugin;
import org.redstom.botapi.injector.Inject;
import org.redstom.botapi.server.IServer;

@BotPlugin(author = "RedsTom", id = "Example", name = "ExampleAddon")
public class Main {

    @Inject({IServer.class})
    public void load(IServer server) {
        server.getLogger().info("Plugin Example chargé !");

        //server.getEventManager().register(new Listener());
    }

    public static void unload() {

    }

}
