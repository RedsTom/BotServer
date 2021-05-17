package eu.redstom.botserver.plugins;

import eu.redstom.botapi.BotPlugin;
import org.reflections.Reflections;

public class Plugin {

    private final String id, name, author, version, description;
    private final Reflections pluginReflections;
    private final Object instance;

    public Plugin(Class<?> mainClass, Reflections pluginReflections) throws IllegalAccessException, InstantiationException {
        BotPlugin plugin = mainClass.getAnnotation(BotPlugin.class);
        this.id = plugin.id();
        this.name = plugin.name();
        this.author = plugin.author();
        this.version = plugin.version();
        this.description = plugin.description();
        this.instance = mainClass.newInstance();
        this.pluginReflections = pluginReflections;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public Object getInstance() {
        return instance;
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
