package eu.redstom.botapi.server;

import eu.redstom.botapi.events.IEventManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;

/**
 * Server that host the plugin
 */
public interface IServer {

    /**
     * @return The event dispatcher of the server
     */
    IEventManager getEventManager();

    /**
     * @return If the server is actually started
     */
    boolean isServerStarted();

    /**
     * @return If the server is actually stopping
     */
    boolean isStopping();

    /**
     * @return The DiscordBot instance
     */
    DiscordApi getApi();

    /**
     * @return The logger to log informations
     */
    Logger getLogger();

    /**
     * @return The prefix of the bot
     */
    String getPrefix();

    /**
     * Stops the server
     */
    void stop();
}
