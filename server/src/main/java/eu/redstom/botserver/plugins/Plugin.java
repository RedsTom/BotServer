package eu.redstom.botserver.plugins;

import eu.redstom.botapi.BotPlugin;
import eu.redstom.botapi.configuration.FileConfiguration;
import eu.redstom.botapi.i18n.I18n;
import eu.redstom.botapi.plugins.IPlugin;
import eu.redstom.botserver.plugins.i18n.I18nImpl;
import eu.redstom.botserver.server.Server;
import org.reflections.Reflections;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class Plugin implements IPlugin {

    private final String id, name, author, version, description;
    private final Reflections pluginReflections;
    private final Object instance;
    private final File pluginFolder;
    private FileConfiguration pluginConfig;
    private final boolean didPluginFolderExist;
    private final I18n i18n;

    public Plugin(Server server, Class<?> mainClass, Reflections pluginReflections)
        throws IllegalAccessException, InstantiationException {
        BotPlugin plugin = mainClass.getAnnotation(BotPlugin.class);
        this.id = plugin.id();
        this.name = plugin.name();
        this.author = plugin.author();
        this.version = plugin.version();
        this.description = plugin.description();
        this.instance = mainClass.newInstance();
        this.pluginReflections = pluginReflections;
        this.pluginFolder = new File(server.getPluginFolder(), id.toLowerCase());
        this.didPluginFolderExist = pluginFolder.exists();
        this.i18n = new I18nImpl(this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Object getInstance() {
        return instance;
    }

    @Override
    public FileConfiguration getConfig() {
        if (this.pluginConfig == null) {
            try {
                this.pluginConfig = new eu.redstom.botserver.config.FileConfiguration(this, "config");
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        return pluginConfig;
    }

    @Override
    public File getPluginFolder() {
        return pluginFolder;
    }

    @Override
    public I18n getTranslationManager() {
        return i18n;
    }

    public Reflections getPluginReflections() {
        return pluginReflections;
    }

    public boolean didFolderExist() {
        return pluginFolder.exists() && didPluginFolderExist;
    }

    public void printInformation() {
        System.out.println(
            "Plugin information : \n" +
                "\tId : " + getId() + "\n" +
                "\tName : " + getName() + "\n" +
                "\tVersion : " + getVersion() + "\n" +
                "\tDescription : " + getDescription() + "\n" +
                "\tAuthors : " + getAuthor() + "\n");
    }
}
