package org.example.exceptions;

import org.example.system.TextColor;

/**
 * Обеспечивает исключение, если возникла ошибка при создании объекта класса Organization
 *
 * @see org.example.Organization
 * @author vnikolaenko
 * @since 1.0
 */
public class BuildOrganizationException extends Exception{
    /**
     * @param message сообщение, которое необходимо вывести
     * @author vnikolaenko
     * @since 1.0
     */
    public BuildOrganizationException(String message){
        super(TextColor.ANSI_RED + message + TextColor.ANSI_RESET);
    }
}
