package eu.redstom.botserver.plugins;

import eu.redstom.botapi.BotPlugin;
import eu.redstom.botapi.configuration.FileConfiguration;
import eu.redstom.botapi.plugins.IPlugin;
import eu.redstom.botserver.server.Server;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;

public class Plugin implements IPlugin {

    private final String id, name, author, version, description;
    private final Reflections pluginReflections;
    private final Object instance;
    private final File pluginFolder;
    private FileConfiguration pluginConfig;

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pluginConfig;
    }

    @Override
    public File getPluginFolder() {
        return pluginFolder;
    }

    public Reflections getPluginReflections() {
        return pluginReflections;
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
