package exceptions;

import system.TextColor;

/**
 * Обеспечивает исключение, если такой команды уже существует
 *
 * @author vnikolaenko
 * @since 1.0
 */
public class UnknownCommandException extends Exception{
    /**
     * @param command неизвестная команда
     * @author vnikolaenko
     * @since 1.0
     */
    public UnknownCommandException(String command){
        super(TextColor.ANSI_RED + "Unknown command: " + command + TextColor.ANSI_RESET);
    }
}
