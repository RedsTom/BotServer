package eu.redstom.botserver.config.parsed;

import com.google.gson.annotations.Expose;
import eu.redstom.botserver.Constants;
import eu.redstom.botserver.config.ConfigFile;

import java.io.FileWriter;
import java.io.IOException;

public final class Config {

    @Expose
    private String token;

    @Expose
    private String prefix;

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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void write(ConfigFile config) throws IOException {
        FileWriter writer = new FileWriter(config);
        writer.write(Constants.GSON_BUILDER.create().toJson(this));
        writer.flush();
        writer.close();
    }
}
