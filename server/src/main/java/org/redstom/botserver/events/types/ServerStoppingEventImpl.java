package org.redstom.botserver.events.types;

import org.javacord.api.DiscordApi;
import org.redstom.botapi.events.types.ServerStoppingEvent;

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
