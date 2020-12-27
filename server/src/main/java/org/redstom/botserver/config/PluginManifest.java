package org.redstom.botserver.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PluginManifest {

    @Expose
    @SerializedName("main-class")
    private String mainClass;

    private PluginManifest(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }
}
