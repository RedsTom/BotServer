package eu.redstom.botserver.console;

import eu.redstom.botapi.server.IServer;
import org.jline.terminal.Terminal;

import java.util.Scanner;

public class CommandThread extends Thread {

    private final Terminal terminal;
    private final IServer server;
    private final Scanner console;

    public CommandThread(IServer server, Terminal terminal) {
        this.server = server;
        this.terminal = terminal;
        this.console = new Scanner(System.in);
        setName("Commands");
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        //noinspection StatementWithEmptyBody
        while (!server.isServerStarted()) ;
        System.out.println("Enter \"?\" or \"help\" to get all the console commands");
        while (!isInterrupted() && console.hasNextLine()) {
            System.out.println(console.nextLine());
        }
        server.stop();
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.console.close();
    }
}