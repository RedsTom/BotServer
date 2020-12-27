package org.redstom.botserver;

import org.redstom.botserver.java.exceptions.PluginAlreadyExistsException;
import org.redstom.botserver.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IOException, PluginAlreadyExistsException {

        Server server = new Server();
        server.start();

    }

}
