package org.redstom.botserver.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.redstom.botserver.plugins.Plugin;

import java.io.*;

public class FileConfiguration implements org.redstom.botapi.configuration.FileConfiguration {

    private final File folder;
    private File file;
    private JsonObject object;

    public FileConfiguration(Plugin plugin, String fileName) throws IOException {
        this.folder = new File("plugins", plugin.getId());
        if (!folder.exists()) folder.mkdirs();
        this.file = new File(folder, fileName + ".json");
        if (!file.exists()) file.createNewFile();
        update();
    }

    public void update() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.object = gson.fromJson(reader, JsonObject.class);
        reader.close();
    }


    public void save() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(gson.toJson(object));
        writer.flush();
        writer.close();
    }

    public JsonObject getValues() {
        return object;
    }
}
