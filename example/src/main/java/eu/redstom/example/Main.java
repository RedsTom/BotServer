package eu.redstom.example;

import eu.redstom.botapi.BotPlugin;
import eu.redstom.botapi.injector.Inject;
import eu.redstom.botapi.plugins.IPlugin;
import eu.redstom.botapi.server.IServer;

@BotPlugin(author = "RedsTom", id = "Example", name = "ExampleAddon")
public class Main {

    public void load(@Inject("server") IServer server, @Inject("plugin") IPlugin plugin) {
        server.getLogger().info("Plugin " + plugin.getName() + " loaded !");
    }

    public void unload(@Inject("server") IServer server, @Inject("plugin") IPlugin plugin) {
        server.getLogger().info("Plugin " + plugin.getName() + " unloaded !");
    }
}
