package eu.redstom.botserver.config;

import java.io.File;

public class PluginFolder extends File {

    public PluginFolder() {
        super("plugins/");
    }

    public boolean checkExist() {
        if (!exists()) {
            mkdir();
            return false;
        }
        return true;
    }

}
