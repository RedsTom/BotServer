package org.redstom.botserver.events.types;

import org.javacord.api.DiscordApi;
import org.redstom.botapi.events.types.ServerStartingEvent;

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
