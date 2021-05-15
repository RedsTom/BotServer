package org.redstom.botserver.events.types;

import org.javacord.api.DiscordApi;
import org.redstom.botapi.events.types.ServerStartedEvent;

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
