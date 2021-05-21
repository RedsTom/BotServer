package eu.redstom.botapi.plugins;

import eu.redstom.botapi.configuration.FileConfiguration;
import eu.redstom.botapi.i18n.I18n;

import java.io.File;

/**
 * Represents a plugin, and passed to the plugins themselves by the auto-loader.
 */
public interface IPlugin {
    /**
     * @return The id of the plugin
     */
    String getId();

    /**
     * @return The name of the plugin
     */
    String getName();

    /**
     * @return The author of the plugin
     */
    String getAuthor();

    /**
     * @return The version of the plugin
     */
    String getVersion();

    /**
     * @return The description of the plugin
     */
    String getDescription();

    /**
     * @return The generated instance of the main class of the plugin
     */
    Object getInstance();

    /**
     * @return The configuration file of the plugin
     */
    FileConfiguration getConfig();

    /**
     * @return The folder where the plugin stores its config files
     */
    File getPluginFolder();

    /**
     * @return The translation manager of the plugin
     */
    I18n getTranslationManager();
}
