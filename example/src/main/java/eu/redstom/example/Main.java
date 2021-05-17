package eu.redstom.example;

import eu.redstom.botapi.BotPlugin;
import eu.redstom.botapi.injector.Inject;
import eu.redstom.botapi.server.IServer;

@BotPlugin(author = "RedsTom", id = "Example", name = "ExampleAddon")
public class Main {

    public void load(@Inject("server") IServer server) {
        server.getLogger().info("Plugin Example chargé !");
    }

    public void unload() {
    }
}
