package exceptions;

import system.TextColor;

public class BuildObjectException extends Exception{
    public BuildObjectException(String message){
        super(TextColor.ANSI_RED + message + TextColor.ANSI_RESET);
    }
}
