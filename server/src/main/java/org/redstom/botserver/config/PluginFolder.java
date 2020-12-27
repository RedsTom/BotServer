package org.redstom.botserver.config;

import java.io.File;
import java.io.IOException;

public class PluginFolder extends File {

    public PluginFolder() {
        super("plugins/");
    }

    public boolean checkExist() throws IOException {
        if (!exists()) {
            mkdir();
            return false;
        }
        return true;
    }

}
