package org.redstom.botserver.plugins;

import org.redstom.botapi.utils.IConsoleManager;

public class Plugin {

    private final String id, name, author, version, description;

    public Plugin(String id, String name, String author, String version, String description) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.version = version;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public void printInformation(IConsoleManager consoleManager) {
        consoleManager.printLine(
                "Plugin information : \n" +
                        "\tId : " + getId() + "\n" +
                        "\tName : " + getName() + "\n" +
                        "\tVersion : " + getVersion() + "\n" +
                        "\tDescription : " + getDescription() + "\n" +
                        "\tAuthors : " + getAuthor() + "\n");
    }
}
