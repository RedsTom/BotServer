package eu.redstom.botapi.plugins;

import eu.redstom.botapi.configuration.FileConfiguration;

import java.io.File;

public interface IPlugin {
    String getId();

    String getName();

    String getAuthor();

    String getVersion();

    String getDescription();

    Object getInstance();

    FileConfiguration getConfig();

    File getPluginFolder();
}
