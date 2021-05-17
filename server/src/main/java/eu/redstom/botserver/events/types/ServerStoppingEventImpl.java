package eu.redstom.botserver.events.types;

import eu.redstom.botapi.events.types.ServerStoppingEvent;
import org.javacord.api.DiscordApi;

/**
 * Event called before the server shuts down
 */
public class ServerStoppingEventImpl implements ServerStoppingEvent {
    private final DiscordApi api;

    public ServerStoppingEventImpl(DiscordApi api) {
        this.api = api;
    }

    @Override
    public DiscordApi getApi() {
        return api;
    }
}
