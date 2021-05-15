package org.redstom.botserver;

import org.redstom.botserver.server.Server;

public class Main {

    public static void main(String[] args) {

        Thread mainThread = new Thread(() -> {
            Server server = new Server();
            server.start();
        });
        mainThread.setName("BotServer - Main");
        mainThread.start();

    }

}
