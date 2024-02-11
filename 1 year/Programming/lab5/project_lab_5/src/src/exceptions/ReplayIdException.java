package exceptions;

import system.TextColor;

public class ReplayIdException extends Exception{
    public ReplayIdException(long id){super(TextColor.ANSI_RED + "ID " + id + " is already used" + TextColor.ANSI_RESET);}
}
