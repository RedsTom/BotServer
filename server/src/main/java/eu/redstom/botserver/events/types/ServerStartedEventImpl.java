package eu.redstom.botserver.events.types;

import eu.redstom.botapi.events.types.ServerStartedEvent;
import org.javacord.api.DiscordApi;

/**
 * Event called when the server has finished loading and the bot has started
 */
public class ServerStartedEventImpl implements ServerStartedEvent {
    private final DiscordApi api;

    public ServerStartedEventImpl(DiscordApi api) {
        this.api = api;
    }

    @Override
    public DiscordApi getApi() {
        return api;
    }
}
