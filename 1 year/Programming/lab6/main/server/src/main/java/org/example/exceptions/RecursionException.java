package org.example.exceptions;

import org.example.system.TextColor;

public class RecursionException extends Exception{
    public RecursionException(){
        super(TextColor.ANSI_RED + "Recursion in file" + TextColor.ANSI_RESET);
    }
}
