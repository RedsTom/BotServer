package org.redstom.botserver.config;

import org.redstom.botserver.Constants;
import org.redstom.botserver.config.parsed.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class ConfigFile extends File {

    public ConfigFile() throws IOException {
        super("config.json");
    }

    public boolean checkExist() throws IOException {
        if (!exists()) {
            createNewFile();
            return false;
        }
        return true;
    }

    public Config parse() throws FileNotFoundException {
        return Constants.GSON_BUILDER.create().fromJson(new FileReader(this), Config.class);
    }
}
