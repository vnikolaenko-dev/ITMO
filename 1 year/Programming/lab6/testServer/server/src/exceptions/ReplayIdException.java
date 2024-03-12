package exceptions;

import system.TextColor;

/**
 * Обеспечивает исключение, если такой ID уже существует
 *
 * @author vnikolaenko
 * @since 1.0
 */
public class ReplayIdException extends Exception {
    /**
     * @param id ID которое уже используется
     * @author vnikolaenko
     * @since 1.0
     */
    public ReplayIdException(long id) {
        super(TextColor.ANSI_RED + "ID " + id + " is already used" + TextColor.ANSI_RESET);
    }

    public ReplayIdException(String id) {
        super(TextColor.ANSI_RED + "ID " + id + " is already used" + TextColor.ANSI_RESET);
    }
}
