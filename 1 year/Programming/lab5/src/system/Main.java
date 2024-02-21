package system;

import managers.Console;

import java.io.File;

/**
 * Главный класс, который запускает программу.
 * Требуется передать путь до файла XML, в котором хранятся данные
 *
 * @author vnikolaenko
 * @since 1.0
 */
public class Main {
    /**
     * Точка начала программы.
     *
     * @param args аргументы из командной строки
     */
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println(TextColor.ANSI_RED + "Something wrong with argument (file_path)" + TextColor.ANSI_RESET);
        }
        else {
            File file = new File(args[0]);
            if (file.canRead() && file.canWrite()){
                Console console = new Console();
                console.start(System.in, args);
            }
            else {
                System.out.println(TextColor.ANSI_RED + "You do not have enough root" + TextColor.ANSI_RESET);
            }
        }
    }
}