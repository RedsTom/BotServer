package eu.redstom.botserver.server;

import eu.redstom.botapi.events.IEventManager;
import eu.redstom.botapi.server.IServer;
import eu.redstom.botserver.config.ConfigFile;
import eu.redstom.botserver.config.PluginFolder;
import eu.redstom.botserver.config.parsed.Config;
import eu.redstom.botserver.console.CommandThread;
import eu.redstom.botserver.events.EventManager;
import eu.redstom.botserver.events.types.ServerStartedEventImpl;
import eu.redstom.botserver.events.types.ServerStartingEventImpl;
import eu.redstom.botserver.plugins.Plugin;
import eu.redstom.botserver.plugins.loader.PluginLoader;
import eu.redstom.botserver.plugins.loader.wirer.ParametersWirer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Server implements IServer {

    private static final Logger LOGGER = LogManager.getLogger();

    private volatile boolean started;
    private DiscordApi api;
    private EventManager eventManager;
    private Config config;
    private PluginLoader pluginLoader;
    private boolean stopping;
    private Map<String, Object> availableParams;
    private PluginFolder pluginFolder;

    public void start() {

        resetVariables().thenAccept((v) -> {

            System.setOut(IoBuilder.forLogger(getLogger()).setLevel(Level.INFO).buildPrintStream());
            System.setErr(IoBuilder.forLogger(getLogger()).setLevel(Level.ERROR).buildPrintStream());

            System.out.println("Server marked as starting");
            System.out.println("");

            new CommandThread(this, null);

            getConfig().thenAccept((config) -> {
                this.config = config;
                System.out.println("Configuration loaded !");

                System.out.println();

                System.out.println("Logging bot in...");
                loginBot().thenAccept((v2) -> {
                    System.out.println("Bot logged in !");

                    System.out.println();

                    System.out.println("Loading plugins...");
                    loadPlugins().thenAccept((v1) -> {
                        System.out.println("Plugins loaded !");
                        // Registers all the javacord events
                        {
                            this.api.addAudioSourceFinishedListener(this.eventManager::dispatch);
                            this.api.addCachedMessagePinListener(this.eventManager::dispatch);
                            this.api.addCachedMessageUnpinListener(this.eventManager::dispatch);
                            this.api.addChannelPinsUpdateListener(this.eventManager::dispatch);
                            this.api.addGroupChannelChangeNameListener(this.eventManager::dispatch);
                            this.api.addGroupChannelCreateListener(this.eventManager::dispatch);
                            this.api.addGroupChannelDeleteListener(this.eventManager::dispatch);
                            this.api.addInteractionCreateListener(this.eventManager::dispatch);
                            this.api.addKnownCustomEmojiChangeNameListener(this.eventManager::dispatch);
                            this.api.addKnownCustomEmojiChangeWhitelistedRolesListener(this.eventManager::dispatch);
                            this.api.addKnownCustomEmojiCreateListener(this.eventManager::dispatch);
                            this.api.addKnownCustomEmojiDeleteListener(this.eventManager::dispatch);
                            this.api.addLostConnectionListener(this.eventManager::dispatch);
                            this.api.addMessageCreateListener(this.eventManager::dispatch);
                            this.api.addMessageDeleteListener(this.eventManager::dispatch);
                            this.api.addMessageEditListener(this.eventManager::dispatch);
                            this.api.addPrivateChannelCreateListener(this.eventManager::dispatch);
                            this.api.addPrivateChannelDeleteListener(this.eventManager::dispatch);
                            this.api.addReactionAddListener(this.eventManager::dispatch);
                            this.api.addReactionRemoveAllListener(this.eventManager::dispatch);
                            this.api.addReactionRemoveListener(this.eventManager::dispatch);
                            this.api.addReconnectListener(this.eventManager::dispatch);
                            this.api.addRoleChangeColorListener(this.eventManager::dispatch);
                            this.api.addRoleChangeHoistListener(this.eventManager::dispatch);
                            this.api.addRoleChangeMentionableListener(this.eventManager::dispatch);
                            this.api.addRoleChangeNameListener(this.eventManager::dispatch);
                            this.api.addRoleChangePermissionsListener(this.eventManager::dispatch);
                            this.api.addRoleChangePositionListener(this.eventManager::dispatch);
                            this.api.addRoleCreateListener(this.eventManager::dispatch);
                            this.api.addRoleDeleteListener(this.eventManager::dispatch);
                            this.api.addServerBecomesAvailableListener(this.eventManager::dispatch);
                            this.api.addServerBecomesUnavailableListener(this.eventManager::dispatch);
                            this.api.addServerChangeAfkChannelListener(this.eventManager::dispatch);
                            this.api.addServerChangeAfkTimeoutListener(this.eventManager::dispatch);
                            this.api.addServerChangeBoostCountListener(this.eventManager::dispatch);
                            this.api.addServerChangeBoostLevelListener(this.eventManager::dispatch);
                            this.api.addServerChangeDefaultMessageNotificationLevelListener(this.eventManager::dispatch);
                            this.api.addServerChangeDescriptionListener(this.eventManager::dispatch);
                            this.api.addServerChangeDiscoverySplashListener(this.eventManager::dispatch);
                            this.api.addServerChangeExplicitContentFilterLevelListener(this.eventManager::dispatch);
                            this.api.addServerChangeIconListener(this.eventManager::dispatch);
                            this.api.addServerChangeModeratorsOnlyChannelListener(this.eventManager::dispatch);
                            this.api.addServerChangeMultiFactorAuthenticationLevelListener(this.eventManager::dispatch);
                            this.api.addServerChangeNameListener(this.eventManager::dispatch);
                            this.api.addServerChangeOwnerListener(this.eventManager::dispatch);
                            this.api.addServerChangePreferredLocaleListener(this.eventManager::dispatch);
                            this.api.addServerChangeRegionListener(this.eventManager::dispatch);
//                            this.api.addServerChangeRulesChannelListener(this.eventManager::dispatch);
                            this.api.addServerChangeServerFeatureListener(this.eventManager::dispatch);
                            this.api.addServerChangeSplashListener(this.eventManager::dispatch);
                            this.api.addServerChangeSystemChannelListener(this.eventManager::dispatch);
                            this.api.addServerChangeVanityUrlCodeListener(this.eventManager::dispatch);
                            this.api.addServerChangeVerificationLevelListener(this.eventManager::dispatch);
                            this.api.addServerChannelChangeNameListener(this.eventManager::dispatch);
                            this.api.addServerChannelChangeNsfwFlagListener(this.eventManager::dispatch);
                            this.api.addServerChannelChangeOverwrittenPermissionsListener(this.eventManager::dispatch);
                            this.api.addServerChannelChangePositionListener(this.eventManager::dispatch);
                            this.api.addServerChannelCreateListener(this.eventManager::dispatch);
                            this.api.addServerChannelDeleteListener(this.eventManager::dispatch);
                            this.api.addServerChannelInviteCreateListener(this.eventManager::dispatch);
                            this.api.addServerChannelInviteDeleteListener(this.eventManager::dispatch);
                            this.api.addServerJoinListener(this.eventManager::dispatch);
                            this.api.addServerLeaveListener(this.eventManager::dispatch);
                            this.api.addServerMemberBanListener(this.eventManager::dispatch);
                            this.api.addServerMemberJoinListener(this.eventManager::dispatch);
                            this.api.addServerMemberLeaveListener(this.eventManager::dispatch);
                            this.api.addServerMemberUnbanListener(this.eventManager::dispatch);
                            this.api.addServerTextChannelChangeSlowmodeListener(this.eventManager::dispatch);
                            this.api.addServerTextChannelChangeTopicListener(this.eventManager::dispatch);
                            this.api.addServerVoiceChannelChangeBitrateListener(this.eventManager::dispatch);
                            this.api.addServerVoiceChannelChangeUserLimitListener(this.eventManager::dispatch);
                            this.api.addServerVoiceChannelMemberJoinListener(this.eventManager::dispatch);
                            this.api.addServerVoiceChannelMemberLeaveListener(this.eventManager::dispatch);
                            this.api.addUserChangeActivityListener(this.eventManager::dispatch);
                            this.api.addUserChangeAvatarListener(this.eventManager::dispatch);
                            this.api.addUserChangeDeafenedListener(this.eventManager::dispatch);
                            this.api.addUserChangeDiscriminatorListener(this.eventManager::dispatch);
                            this.api.addUserChangeMutedListener(this.eventManager::dispatch);
                            this.api.addUserChangeNameListener(this.eventManager::dispatch);
                            this.api.addUserChangeNicknameListener(this.eventManager::dispatch);
                            this.api.addUserChangeSelfDeafenedListener(this.eventManager::dispatch);
                            this.api.addUserChangeSelfMutedListener(this.eventManager::dispatch);
                            this.api.addUserChangeStatusListener(this.eventManager::dispatch);
                            this.api.addUserRoleAddListener(this.eventManager::dispatch);
                            this.api.addUserRoleRemoveListener(this.eventManager::dispatch);
                            this.api.addUserStartTypingListener(this.eventManager::dispatch);
                            this.api.addVoiceServerUpdateListener(this.eventManager::dispatch);
                            this.api.addVoiceStateUpdateListener(this.eventManager::dispatch);
                            this.api.addWebhooksUpdateListener(this.eventManager::dispatch);
                        }

                        eventManager.dispatch(new ServerStartedEventImpl(this.api));

                        System.out.println("Server started !");
                        this.started = true;
                    });
                });
            });
        });
    }

    public void stop() {
        this.stopping = true;
        System.out.println("Server stopping...");
        for (Plugin plugin : pluginLoader.getPlugins()) {
            ParametersWirer<?> wirer = new ParametersWirer<>(plugin.getInstance().getClass());

            this.availableParams.put("plugin", plugin);

            try {
                wirer.invoke(wirer.getMethod("unload"), plugin.getInstance(), new Object[0], availableParams);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server stopped !");
        System.exit(0);
    }

    private CompletableFuture<Void> resetVariables() {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            this.availableParams = new HashMap<>();
            this.availableParams.put("server", this);
            this.pluginLoader = new PluginLoader();
            this.eventManager = new EventManager(availableParams);
            this.started = false;
            completableFuture.complete(null);
        });
        return completableFuture;
    }

    public PluginFolder getPluginFolder() {
        if (this.pluginFolder == null) {
            PluginFolder folder = new PluginFolder();
            if (folder.checkExist()) {
                this.pluginFolder = folder;
                return folder;
            }
        }
        return this.pluginFolder;
    }

    private CompletableFuture<Config> getConfig() {
        CompletableFuture<Config> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            System.out.println("Loading configuration...");
            try {
                ConfigFile configFile = new ConfigFile();
                if (!configFile.checkExist()) {
                    Config config = Config.empty();
                    config.setToken("");
                    config.setPrefix("!");
                    config.write(configFile);
                }
                completableFuture.complete(configFile.parse());
            } catch (IOException exception) {
                exception.printStackTrace();
                completableFuture.complete(null);
            }
        });
        return completableFuture;
    }

    private CompletableFuture<Void> loadPlugins() {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            pluginLoader.load(this, eventManager, availableParams);
            eventManager.dispatch(new ServerStartingEventImpl(this.api));
            completableFuture.complete(null);
        });
        return completableFuture;
    }

    private CompletableFuture<Void> loginBot() {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            DiscordApiBuilder apiBuilder = new DiscordApiBuilder().setToken(config.getToken());
            apiBuilder.setAllIntents();
            this.api = apiBuilder.login().join();
            completableFuture.complete(null);
        });
        return completableFuture;
    }

    @Override
    public IEventManager getEventManager() {
        return this.eventManager;
    }

    @Override
    public boolean isServerStarted() {
        return this.started;
    }

    @Override
    public boolean isStopping() {
        return this.stopping;
    }

    @Override
    public DiscordApi getApi() {
        return this.api;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public String getPrefix() {
        return config.getPrefix();
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public PluginLoader getPluginLoader() {
        return pluginLoader;
    }

    public Map<String, Object> getAvailableParams() {
        return availableParams;
    }
}
