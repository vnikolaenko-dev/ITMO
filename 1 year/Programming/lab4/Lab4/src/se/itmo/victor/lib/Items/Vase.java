package se.itmo.victor.lib.Items;

import se.itmo.victor.lib.Exceptions.NoCookieException;
import se.itmo.victor.lib.System.Colors;

import java.util.ArrayList;

public class Vase {
    protected ArrayList<Candy> candies = new ArrayList<Candy>();

    // ВЛОЖЕННЫй КЛАСС
    protected class Candy {
        String name;

        public Candy(String name) {
            this.name = name;
        }
    }

    public Vase(int n) {

        for (int i = 0; i < n; ++i) {
            if (Math.random() <= 0.7)
                this.candies.add(new Candy("Печенье"));
            else
                this.candies.add(new Candy("Конфета"));
        }

    }

    public void checkCandies() {
        int cookies = 0;
        int other = 0;

        for (int i = 0; i < candies.size(); ++i) {
            if (candies.get(i).name == "Печенье") cookies += 1;
            else other += 1;
        }
        System.out.println("Ваза с ");
        System.out.println(Colors.ANSI_BLUE + cookies + " Печеньем" + Colors.ANSI_RESET);
        System.out.println(other + " Другими сладостями");
    }

    public void getCookies(int n) throws NoCookieException {
        ArrayList<Candy> a = (ArrayList<Candy>) candies.clone();
        int k = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < a.size(); ++j) {
                if (a.get(j).name == "Печенье") {
                    a.remove(j);
                    k++;
                    break;
                }
            }
        }
        if (k < n) throw new NoCookieException(n, k);

        this.candies = a;
        System.out.println(Colors.ANSI_BLUE + "Малыш взял " + n + " Печеньки из ВАЗЫ, теперь в комнате" + Colors.ANSI_RESET);
        this.checkCandies();
    }
}
