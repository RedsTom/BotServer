package org.redstom.botserver.console;

import org.redstom.botapi.utils.IConsoleManager;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class CliManager implements IConsoleManager {

    private final Scanner scanner;
    private boolean commandStarted;

    public CliManager() {
        this.scanner = new Scanner(System.in);
        this.commandStarted = false;
    }

    public CompletableFuture<String> readLine(String prompt, boolean newLine) {
        System.out.print(prompt + (newLine ? "\n" : " "));
        return CompletableFuture.supplyAsync(scanner::nextLine);
    }

    public CompletableFuture<String> readLine(String prompt) {
        return readLine(prompt, false);
    }

    public void startCommandProcess() {
        this.commandStarted = true;
        askForCommand();
    }

    public void askForCommand() {
        if (!commandStarted) return;
        readLine("> ").thenAccept((answer) -> {
            System.out.println(answer);
            askForCommand();
        });
    }

    public void printBlankLine() {
        printLine("");
    }

    public void printLine(String message) {
        boolean started = commandStarted;
        if (started) {
            stopCommandProcess();
        }
        System.out.println(message);
        if (started) {
            startCommandProcess();
        }
    }

    public void stopCommandProcess() {
        this.commandStarted = false;
        System.out.println();
    }

}
