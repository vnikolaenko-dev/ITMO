package org.example.exceptions;

import org.example.system.TextColor;

/**
 * Обеспечивает исключение, если такой возникла ошибка с аргументом
 *
 * @author vnikolaenko
 * @since 1.0
 */
public class WrongArgumentException extends Exception{
    /**
     * @param argument название аргумента, который был введен неправильно
     * @author vnikolaenko
     * @since 1.0
     */
    public WrongArgumentException(String argument){
        super(TextColor.ANSI_RED + "Something wrong with input argument: " + argument + TextColor.ANSI_RESET);
    }
}
