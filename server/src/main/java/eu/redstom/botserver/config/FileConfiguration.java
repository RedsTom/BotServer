package eu.redstom.botserver.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.redstom.botserver.plugins.Plugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileConfiguration implements eu.redstom.botapi.configuration.FileConfiguration {

    private final File folder;
    private File file;
    private Map<String, Object> values;

    public FileConfiguration(Plugin plugin, String fileName) throws IOException {
        this.folder = new File(new PluginFolder(), plugin.getId());
        if (!folder.exists()) folder.mkdirs();
        this.file = new File(folder, fileName + ".json");
        if (!file.exists()) {
            file.createNewFile();
            this.values = new HashMap<>();
            save();
        }
        update();
    }

    public void update() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.values = gson.fromJson(reader, Map.class);
        reader.close();
    }


    public void save() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(gson.toJson(values));
        writer.flush();
        writer.close();
    }

    public Map<String, Object> getValues() {
        return values;
    }
}
