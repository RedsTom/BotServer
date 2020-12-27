package org.redstom.botserver.server;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.redstom.botapi.BotPlugin;
import org.redstom.botapi.events.IEventDispatcher;
import org.redstom.botapi.events.types.MessageCreateEvent;
import org.redstom.botapi.events.types.ServerStartedEvent;
import org.redstom.botapi.events.types.ServerStartingEvent;
import org.redstom.botapi.server.IServer;
import org.redstom.botapi.utils.IConsoleManager;
import org.redstom.botserver.Constants;
import org.redstom.botserver.config.ConfigFile;
import org.redstom.botserver.config.PluginFolder;
import org.redstom.botserver.config.PluginManifest;
import org.redstom.botserver.config.parsed.Config;
import org.redstom.botserver.console.CliManager;
import org.redstom.botserver.events.EventDispatcher;
import org.redstom.botserver.exceptions.PluginLoadingException;
import org.redstom.botserver.java.JarFileLoader;
import org.redstom.botserver.java.exceptions.MissingAnnotationException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class Server implements IServer {

    private CliManager manager;
    private boolean started;
    private DiscordApi api;
    private EventDispatcher eventDispatcher;
    private Config config;

    public Server() {

    }

    public void start() throws IOException, PluginLoadingException, ClassNotFoundException, NoSuchMethodException {
        this.started = true;
        this.manager = new CliManager();
        this.eventDispatcher = new EventDispatcher();

        manager.printLine("Server marked as starting");

        manager.printBlankLine();

        manager.printLine("Loading configuration...");
        this.config = getConfig();
        manager.printLine("Configuration loaded !");

        manager.printBlankLine();

        manager.printLine("Loading plugins...");
        updatePlugins();
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

    private void updatePlugins() throws IOException, ClassNotFoundException, NoSuchMethodException {

        PluginFolder pluginFolder = new PluginFolder();
        if (pluginFolder.checkExist() && pluginFolder.listFiles() != null) {

            for (File file : pluginFolder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    System.out.println("-------[ Plugin " + file.getName() + " ]-------");
                    try {
                        JarFileLoader cl = new JarFileLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());

                        BufferedReader bReader = new BufferedReader(new InputStreamReader(cl.getResourceAsStream("manifest.json")));
                        PluginManifest config = Constants.GSON_BUILDER.create().fromJson(bReader, PluginManifest.class);

                        Class<?> mainClass = cl.loadClass(config.getMainClass());
                        BotPlugin annotation = mainClass.getAnnotation(BotPlugin.class);

                        if (annotation == null) {
                            throw new MissingAnnotationException("Cannot find the BotPlugin annotation on the main class !");
                        }

                        System.out.println("Plugin informations : ");
                        System.out.println("\tId : " + annotation.id());
                        System.out.println("\tName : " + annotation.name());
                        System.out.println("\tVersion : " + annotation.version());
                        System.out.println("\tDescription : " + annotation.description());
                        System.out.println("\tAuthors : " + annotation.author());

                        mainClass.getMethod("load", IServer.class).invoke(this, this);
                        mainClass.getMethod("unload", IServer.class);

                    } catch (ClassNotFoundException exception) {
                        System.err.println("Cannot find the provided main class ! Please fix it and reload the server.");
                        throw new ClassNotFoundException("Invalid manifest.json : Cannot find the precised main-class !");
                    } catch (NullPointerException exception) {
                        throw new FileNotFoundException("Cannot find the manifest.json in the jar file !");
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new NoSuchMethodException("Cannot find the two required methods in the main class : load() and unload()");
                    }
                    System.out.println("------------------------------------");
                }
            }
        }
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
}
