package managers;

import system.ReaderXML;
import system.TextColor;

import java.io.InputStream;
import java.util.Scanner;

public class Console {
    public void start(InputStream input, String[] args) {
        Scanner scanner = new Scanner(input);
        CommandManager commandManager = new CommandManager();
        new CollectionManager();

        try {
            System.out.println("Downloading data from file...");
            ReaderXML.read(args[0]);
            System.out.println(TextColor.ANSI_GREEN + "Everything is OK" + TextColor.ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error while reading file\n" + args[0]);
            System.exit(0);
        }

        System.out.println("Welcome to app!");
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            try {
                commandManager.startExecuting(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
