package eu.redstom.botserver.config;

import eu.redstom.botserver.plugins.Plugin;
import org.simpleyaml.configuration.file.YamlConfiguration;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;

public class FileConfiguration implements eu.redstom.botapi.configuration.FileConfiguration {

    private final File file;
    private YamlConfiguration config;

    public FileConfiguration(Plugin plugin, String fileName) throws IOException, InvalidConfigurationException {
        File folder = new File(new PluginFolder(), plugin.getId());
        if (!folder.exists()) folder.mkdirs();
        this.file = new File(folder, fileName + ".yml");
        if (!file.exists()) {
            file.createNewFile();
            this.config = new YamlConfiguration();
            save();
        }
        update();
    }

    public FileConfiguration(File folder, String fileName) throws IOException, InvalidConfigurationException {
        if (!folder.exists()) folder.mkdirs();
        this.file = new File(folder, fileName + ".yml");
        if (!file.exists()) {
            file.createNewFile();
            this.config = new YamlConfiguration();
            save();
        }
        update();
    }

    public FileConfiguration(String config) throws IOException, InvalidConfigurationException {
        this.file = new File(config + ".yml");
        if (!file.exists()) {
            file.createNewFile();
            this.config = new YamlConfiguration();
            save();
        }
        update();
    }

    public void update() throws IOException, InvalidConfigurationException {
        config = new YamlConfiguration();
        config.load(file);
    }


    public void save() throws IOException {
        config.save(file);
    }

    public YamlConfiguration getValues() {
        return config;
    }
}
