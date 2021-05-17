package eu.redstom.botserver.events.types;

import eu.redstom.botapi.events.types.ServerStartingEvent;
import org.javacord.api.DiscordApi;

/**
 * Event called when all the plugins were loaded
 */
public class ServerStartingEventImpl implements ServerStartingEvent {
    private final DiscordApi api;

    public ServerStartingEventImpl(DiscordApi api) {
        this.api = api;
    }

    @Override
    public DiscordApi getApi() {
        return api;
    }
}
