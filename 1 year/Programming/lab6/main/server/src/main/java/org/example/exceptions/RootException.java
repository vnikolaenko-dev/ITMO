package org.example.exceptions;

import org.example.system.TextColor;


public class RootException extends Exception{
    public RootException(String message){
        super(TextColor.ANSI_RED + message + TextColor.ANSI_RESET);
    }
}
