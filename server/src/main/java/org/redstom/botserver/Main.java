package org.redstom.botserver;

import org.redstom.botserver.exceptions.PluginLoadingException;
import org.redstom.botserver.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws PluginLoadingException, ClassNotFoundException, NoSuchMethodException, IOException {

        Server server = new Server();
        server.start();

    }

}
