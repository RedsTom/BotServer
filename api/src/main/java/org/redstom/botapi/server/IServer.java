package org.redstom.botapi.server;

import org.javacord.api.DiscordApi;
import org.redstom.botapi.events.IEventDispatcher;
import org.redstom.botapi.utils.IConsoleManager;

public interface IServer {

    IConsoleManager getConsoleManager();

    IEventDispatcher getEventDispatcher();

    boolean isServerStarted();

    DiscordApi getApi();

}
