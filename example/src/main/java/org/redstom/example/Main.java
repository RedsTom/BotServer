package org.redstom.example;

import org.redstom.botapi.BotPlugin;
import org.redstom.botapi.injector.Inject;
import org.redstom.botapi.server.IServer;

@BotPlugin(author = "RedsTom", id = "Example", name = "ExampleAddon")
public class Main {

    public void load(@Inject("server") IServer server) {
        server.getLogger().info("Plugin Example chargé !");
    }

    public void unload() {
    }
}
