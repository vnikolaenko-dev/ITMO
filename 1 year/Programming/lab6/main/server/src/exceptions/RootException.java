package exceptions;

import system.TextColor;


public class RootException extends Exception{
    public RootException(String message){
        super(TextColor.ANSI_RED + message + TextColor.ANSI_RESET);
    }
}
