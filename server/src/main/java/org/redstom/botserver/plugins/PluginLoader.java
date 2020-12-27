package org.redstom.botserver.plugins;

import org.redstom.botapi.BotPlugin;
import org.redstom.botapi.server.IServer;
import org.redstom.botserver.Constants;
import org.redstom.botserver.config.PluginFolder;
import org.redstom.botserver.config.PluginManifest;
import org.redstom.botserver.java.JarFileLoader;
import org.redstom.botserver.java.exceptions.MissingAnnotationException;
import org.redstom.botserver.java.exceptions.PluginAlreadyExistsException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader {

    private final List<Plugin> plugins;
    private final List<String> ids;

    public PluginLoader() {
        this.plugins = new ArrayList<>();
        this.ids = new ArrayList<>();
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public List<String> getIds() {
        return ids;
    }

    public void addPlugin(Plugin plugin) throws PluginAlreadyExistsException {
        if (ids.contains(plugin.getId()))
            throw new PluginAlreadyExistsException("There is already a plugin with the ID " + plugin.getId() + " loaded !");
        this.plugins.add(plugin);
        this.ids.add(plugin.getId());
    }

    public void load(IServer source) throws IOException, ClassNotFoundException, NoSuchMethodException, PluginAlreadyExistsException {
        PluginFolder pluginFolder = new PluginFolder();
        if (pluginFolder.checkExist() && pluginFolder.listFiles() != null) {

            for (File file : pluginFolder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    source.getConsoleManager().printLine("-------[ Plugin " + file.getName() + " ]-------");
                    try {
                        JarFileLoader cl = new JarFileLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());

                        if (cl.getResourceAsStream("manifest.json") == null)
                            throw new FileNotFoundException("Cannot find the manifest.json in the jar file !");

                        // noinspection ConstantConditions
                        BufferedReader bReader = new BufferedReader(new InputStreamReader(cl.getResourceAsStream("manifest.json")));
                        PluginManifest config = Constants.GSON_BUILDER.create().fromJson(bReader, PluginManifest.class);

                        Class<?> mainClass = cl.loadClass(config.getMainClass());
                        BotPlugin annotation = mainClass.getAnnotation(BotPlugin.class);

                        if (annotation == null)
                            throw new MissingAnnotationException("Cannot find the BotPlugin annotation on the main class !");


                        Plugin plugin = new Plugin(annotation.id(), annotation.name(), annotation.author(), annotation.version(), annotation.description());
                        plugin.printInformation(source.getConsoleManager());

                        mainClass.getMethod("load", IServer.class).invoke(this, source);
                        mainClass.getMethod("unload", IServer.class);

                        this.addPlugin(plugin);

                    } catch (ClassNotFoundException exception) {
                        throw new ClassNotFoundException("Invalid manifest.json : Cannot find the precised main-class !");
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new NoSuchMethodException("Cannot find the two required methods in the main class : load() and unload()");
                    }
                    source.getConsoleManager().printLine("------------------------------------");
                }
            }
        }
    }

}
