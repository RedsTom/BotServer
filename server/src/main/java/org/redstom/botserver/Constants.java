package org.redstom.botserver;

import com.google.gson.GsonBuilder;

public class Constants {

    public static final GsonBuilder GSON_BUILDER = new GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation();

}
