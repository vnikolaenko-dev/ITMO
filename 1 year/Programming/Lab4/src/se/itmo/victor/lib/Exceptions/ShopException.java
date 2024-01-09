package se.itmo.victor.lib.Exceptions;

import se.itmo.victor.lib.System.Colors;

public class ShopException extends RuntimeException{
    public ShopException(){
        super(Colors.ANSI_RED + "\n\n-------------------------\n" + "ОШИКА ПРИ ПОКУПКЕ" + "\n-------------------------\n\n" + Colors.ANSI_RESET);
    }
}
