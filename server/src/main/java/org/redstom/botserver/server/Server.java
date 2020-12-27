package org.redstom.botserver.server;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.redstom.botapi.events.IEventDispatcher;
import org.redstom.botapi.events.types.MessageCreateEvent;
import org.redstom.botapi.events.types.ServerStartedEvent;
import org.redstom.botapi.events.types.ServerStartingEvent;
import org.redstom.botapi.server.IServer;
import org.redstom.botapi.utils.IConsoleManager;
import org.redstom.botserver.config.ConfigFile;
import org.redstom.botserver.config.parsed.Config;
import org.redstom.botserver.console.CliManager;
import org.redstom.botserver.events.EventDispatcher;
import org.redstom.botserver.java.exceptions.PluginAlreadyExistsException;
import org.redstom.botserver.plugins.PluginLoader;

import java.io.IOException;

public class Server implements IServer {

    private CliManager manager;
    private boolean started;
    private DiscordApi api;
    private EventDispatcher eventDispatcher;
    private Config config;
    private PluginLoader pluginLoader;

    public void start() throws IOException, ClassNotFoundException, NoSuchMethodException, PluginAlreadyExistsException {
        this.started = true;
        this.manager = new CliManager();
        this.eventDispatcher = new EventDispatcher();
        this.pluginLoader = new PluginLoader();

        manager.printLine("Server marked as starting");

        manager.printBlankLine();

        manager.printLine("Loading configuration...");
        this.config = getConfig();
        manager.printLine("Configuration loaded !");

        manager.printBlankLine();

        manager.printLine("Loading plugins...");
        pluginLoader.load(this);
        eventDispatcher.dispatch(new ServerStartingEvent());
        manager.printLine("Plugins loaded !");

        manager.printBlankLine();

        manager.printLine("Logging bot in...");
        DiscordApiBuilder apiBuilder = new DiscordApiBuilder().setToken(config.getToken()).setTotalShards(config.getShards());
        this.api = apiBuilder.login().join();
        this.api.addMessageCreateListener((event) -> this.eventDispatcher.dispatch(new MessageCreateEvent(event)));
        manager.printLine("Bot logged in !");


        eventDispatcher.dispatch(new ServerStartedEvent());
        manager.printLine("Server started !");
    }

    private Config getConfig() throws IOException {
        ConfigFile configFile = new ConfigFile();
        if (!configFile.checkExist()) {
            Config config = Config.empty();
            config.setToken("");
            config.setShards(1);
            config.write(configFile);
        }
        return configFile.parse();
    }

    @Override
    public IConsoleManager getConsoleManager() {
        return this.manager;
    }

    @Override
    public IEventDispatcher getEventDispatcher() {
        return this.eventDispatcher;
    }

    @Override
    public boolean isServerStarted() {
        return this.started;
    }

    @Override
    public DiscordApi getApi() {
        return this.api;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public PluginLoader getPluginLoader() {
        return pluginLoader;
    }
}
