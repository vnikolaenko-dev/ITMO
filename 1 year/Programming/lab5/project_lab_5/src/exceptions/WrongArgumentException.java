package exceptions;

import system.TextColor;

public class WrongArgumentException extends Exception{
    public WrongArgumentException(String argument){
        super(TextColor.ANSI_RED + "Something wrong with input argument: " + argument + TextColor.ANSI_RESET);
    }
}
