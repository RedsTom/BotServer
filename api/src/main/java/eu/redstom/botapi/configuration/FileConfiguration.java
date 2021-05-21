package eu.redstom.botapi.configuration;

import org.simpleyaml.configuration.file.YamlConfiguration;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.IOException;

/**
 * Represents the configuration of the plugin
 */
public interface FileConfiguration {

    /**
     * Updates the content of the values map from the file
     *
     * @throws IOException                   If the file cannot be read
     * @throws InvalidConfigurationException If the file cannot be parsed
     */
    void update() throws IOException, InvalidConfigurationException;

    /**
     * Saves the content of the map into the file
     *
     * @throws IOException If the file cannot be written
     */
    void save() throws IOException;

    /**
     * @return The values of the file as an hashmap
     */
    YamlConfiguration getValues();

}
