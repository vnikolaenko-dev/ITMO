package managers;

import filelogic.ReaderXML;
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
    public static String data_path = null;
    /**
     * Начните выполнять команды из InputStream.
     *
     * @param args путь до файла
     */
    public void start(String[] args) {
        new CommandManager();
        try {
            System.out.println("Downloading data from file...");
            data_path = args[1];
            ReaderXML.read(data_path);
            System.out.println(TextColor.ANSI_BLUE + "Everything is OK." + TextColor.ANSI_RESET);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error while reading file\n" + args[1]);
            System.exit(0);
        }

        System.out.println("Welcome to app!");
    }
}
