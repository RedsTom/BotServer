package org.redstom.example;

import org.jetbrains.annotations.NotNull;
import org.redstom.botapi.BotPlugin;
import org.redstom.botapi.server.IServer;
import org.redstom.example.events.ServerStartedListener;

@BotPlugin(author = "RedsTom", id = "Example", name = "ExampleAddon")
public class Main {

    public static void load(@NotNull IServer server) {
        server.getConsoleManager().printLine("Plugin Example chargé !");

        server.getEventDispatcher().register(ServerStartedListener.class);
    }

    public static void unload(@NotNull IServer server) {

    }

}
