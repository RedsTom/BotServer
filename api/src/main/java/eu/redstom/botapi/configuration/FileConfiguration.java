package eu.redstom.botapi.configuration;

import java.io.IOException;
import java.util.Map;

/**
 * Represents the configuration of the plugin
 */
public interface FileConfiguration {

    /**
     * Updates the content of the values map from the file
     *
     * @throws IOException If the file cannot be read
     */
    void update() throws IOException;

    /**
     * Saves the content of the map into the file
     *
     * @throws IOException If the file cannot be written
     */
    void save() throws IOException;

    /**
     * @return The values of the file as an hashmap
     */
    Map<String, Object> getValues();

}
