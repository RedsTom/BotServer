package org.redstom.botserver.config.parsed;

import com.google.gson.annotations.Expose;
import org.redstom.botserver.Constants;
import org.redstom.botserver.config.ConfigFile;

import java.io.FileWriter;
import java.io.IOException;

public final class Config {

    @Expose
    private String token;

    private Config() {

    }

    public static Config empty() {
        return new Config();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void write(ConfigFile config) throws IOException {
        FileWriter writer = new FileWriter(config);
        writer.write(Constants.GSON_BUILDER.create().toJson(this));
        writer.flush();
        writer.close();
    }

}
