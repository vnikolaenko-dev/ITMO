package se.itmo.victor.lib.Exceptions;

import se.itmo.victor.lib.System.Colors;

public class NoCookieException extends Exception {
    int need = 0;
    int in_vase = 0;

    public NoCookieException(int need, int in_vase) {
        super(Colors.ANSI_RED + "\n\n-------------------------\n" + "В ВАЗЕ - " + in_vase + "\nНЕОБЪОДИМО - " + need + "\n-------------------------\n\n" + Colors.ANSI_RESET);
        this.in_vase = in_vase;
        this.need = need;
    }
}
