package exceptions;

import system.TextColor;

public class UnknownCommandException extends Exception{
    public UnknownCommandException(String command){
        super(TextColor.ANSI_RED + "Unknown command: " + command + TextColor.ANSI_RESET);
    }
}
