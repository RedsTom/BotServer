package eu.redstom.botserver.plugins.loader;

import eu.redstom.botapi.BotPlugin;
import eu.redstom.botapi.events.SelfRegisteringListener;
import eu.redstom.botserver.config.PluginFolder;
import eu.redstom.botserver.events.EventManager;
import eu.redstom.botserver.plugins.Plugin;
import eu.redstom.botserver.plugins.loader.exceptions.MissingAnnotationException;
import eu.redstom.botserver.plugins.loader.exceptions.PluginAlreadyExistsException;
import eu.redstom.botserver.plugins.loader.java.FileUtils;
import eu.redstom.botserver.plugins.loader.java.JarFileLoader;
import eu.redstom.botserver.plugins.loader.wirer.ParametersWirer;
import eu.redstom.botserver.server.Server;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        // Throws an exception if the plugin is already loaded
        if (ids.contains(plugin.getId())) {
            throw new PluginAlreadyExistsException("There is already a plugin with the ID " + plugin.getId()
                + " loaded !");
        }
        // Adds the plugin to the list of loaded plugins
        this.plugins.add(plugin);
        this.ids.add(plugin.getId());
    }

    public void load(Server server, EventManager manager, Map<String, Object> availableParams) {
        // Gets the plugin folder
        PluginFolder pluginFolder = new PluginFolder();
        if (pluginFolder.checkExist() && pluginFolder.listFiles() != null) {
            // Loop for all files in the plugin folder
            // Checks if the file is a jar file
            // Loads the plugin.
            Arrays.stream(pluginFolder.listFiles())
                .filter(file -> file.isFile() && file.getName().endsWith(".jar"))
                .forEach(file -> loadPlugin(server, file, manager, availableParams));
        }
    }

    private void loadPlugin(Server server, File file, EventManager eventManager, Map<String, Object> availableParams) {
        try {
            // Gets the classpath of the provided file.
            JarFileLoader cl = new JarFileLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());

            // Configures the Reflections for the plugin
            ConfigurationBuilder reflectionsConfig = new ConfigurationBuilder()
                .setScanners(
                    new SubTypesScanner(false),
                    new ResourcesScanner(),
                    new TypeAnnotationsScanner()
                )
                .addClassLoader(cl)
                .setUrls(ClasspathHelper.forClassLoader(cl));

            Reflections reflections = new Reflections(reflectionsConfig);

            // Gets all the @BotPlugin annotations.
            List<Class<?>> botPlugins = new ArrayList<>(reflections.getTypesAnnotatedWith(BotPlugin.class));
            // Throws an exception if there is more or less than one @BotPlugin.
            if (botPlugins.size() > 1) {
                throw new Exception(
                    "There can't be more than one class annotated with @BotPlugin annotation per plugin"
                );
            }
            if (botPlugins.size() < 1) {
                throw new Exception("There must be at least one class annotated with @BotPlugin per plugin");
            }

            // Gets the @BotPlugin's class
            Class<?> mainClass = botPlugins.get(0);
            // Gets the @BotPlugin
            BotPlugin annotation = mainClass.getAnnotation(BotPlugin.class);

            // Reconfigures the Reflections for this plugin
            reflectionsConfig.setInputsFilter(new FilterBuilder().includePackage(mainClass.getPackage().getName()));
            reflections = new Reflections(reflectionsConfig);

            // Informates the user if the loader cannot find the @BotPlugin annotation.
            if (annotation == null) {
                throw new MissingAnnotationException("Cannot find the BotPlugin annotation on the main class !");
            }


            System.out.println("-------[ Plugin " + file.getName() + " ]-------");

            // Initializes a new plugin with the informations of the @BotPlugin annotation.
            Plugin plugin = new Plugin(server, mainClass, reflections);
            plugin.printInformation();

            if (!plugin.didFolderExist()) {
                if (!plugin.getPluginFolder().exists()) {
                    plugin.getPluginFolder().mkdir();
                }
                for (URL url : cl.getURLs()) {
                    url = new URL("jar:" + url + "!/");
                    URLConnection uCon = url.openConnection();
                    if (!(uCon instanceof JarURLConnection con)) continue;
                    FileUtils.copyJarResourcesRecursively(plugin.getPluginFolder(), con);
                }
            }

            ParametersWirer<?> pluginWirer = new ParametersWirer<>(plugin.getInstance().getClass());

            // Gets the "load" from the Main class
            availableParams.put("plugin", plugin);

            Method loadMethod = pluginWirer.getMethod("load");

            if (loadMethod == null) {
                throw new NoSuchMethodException("Cannot find the load method in the main class");
            }
            pluginWirer.invoke(pluginWirer.getMethods("load"), plugin.getInstance(), new Object[0], availableParams);

            // Check if the "unload" method exists
            pluginWirer.getMethod("unload");

            // Load the @SelfRegisteringListener
            reflections.getTypesAnnotatedWith(SelfRegisteringListener.class).forEach((clazz) -> {
                try {
                    if (clazz.getConstructors().length > 1) {
                        throw new RuntimeException(
                            "The class cannot have more than one constructor if it is SelfRegistering"
                        );
                    }
                    ParametersWirer<?> wirer = new ParametersWirer<>(clazz);
                    eventManager.register(wirer.newInstance(clazz.getConstructors()[0], availableParams), plugin);
                } catch (InstantiationException | IllegalAccessException e) {
                    System.err.println("Cannot auto register the @SelfRegisteringListener " + clazz.getName());
                    e.printStackTrace();
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    System.err.println("Cannot autowire the constructor of the @SelfRegisteringListener "
                        + clazz.getName());
                    e.printStackTrace();
                }
            });

            // Adds the plugin to the plugin list
            this.addPlugin(plugin);
            System.out.println("------------------------------------");

        } catch (ClassNotFoundException e) {
            System.err.println("Invalid manifest.json : Cannot find the precised main-class !");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("Cannot find the two required methods in the main class : load() and unload()");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
