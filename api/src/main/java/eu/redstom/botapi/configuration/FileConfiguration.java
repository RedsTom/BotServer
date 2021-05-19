package eu.redstom.botapi.configuration;

import java.io.IOException;
import java.util.Map;

public interface FileConfiguration {

    void update() throws IOException;

    void save() throws IOException;

    Map<?, ?> getValues();

}
