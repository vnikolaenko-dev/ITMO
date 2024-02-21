package managers;

import system.ReaderXML;
import system.TextColor;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Данный класс отвечает за чтение командной строки
 * Обеспечивает связь между пользователем и командами (CommandManager)
 *
 * @see CommandManager
 * @author vnikolaenko
 * @since 1.0
 */
public class Console {

    public static String data_path;
    /**
     * Начните выполнять команды из InputStream.
     *
     * @param input откуда происходит чтение
     * @param args путь до файла
     */
    public void start(InputStream input, String[] args) {
        Scanner scanner = new Scanner(input);
        CommandManager commandManager = new CommandManager();
        new CollectionManager();
        try {
            System.out.println("Downloading data from file...");
            ReaderXML.read(args[0], true);
            data_path = args[0];
            System.out.println(TextColor.ANSI_BLUE + "Everything is OK." + TextColor.ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error while reading file\n" + args[0]);
            System.exit(0);
        }

        System.out.println("Welcome to app!");
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            if (!command.isEmpty()){
                try {
                    commandManager.startExecuting(command);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
