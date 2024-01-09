package se.itmo.victor.lib.Entyties;

import se.itmo.victor.lib.Exceptions.NoCookieException;
import se.itmo.victor.lib.Exceptions.ShopException;
import se.itmo.victor.lib.Items.*;
import se.itmo.victor.lib.Items.Sweety;
import se.itmo.victor.lib.System.Colors;

import java.util.ArrayList;

public class Malish extends Entity implements Thinking {
    protected int money = 0;
    protected boolean knife = false;
    protected String place = "";
    String letter = "Я на вирху у Калсона, который живет на крыше";

    // ВЛОЖЕННЫЙ КЛАСС
    public class Wallet {
        public ArrayList<Coin> coin_list = new ArrayList<Coin>();

        public String content() {
            return "\n" + this.coin_list;
        }
    }

    // ВЛОЖЕННЫЙ КЛАСС
    public class Pocket {
        public ArrayList<Sweety> sweety_list = new ArrayList<Sweety>();

        public void addSweety(Sweety sweety) {
            this.sweety_list.add(sweety);
        }

        public String content() {
            return "\n" + this.sweety_list;
        }
    }

    // ЛОКАЛЬНЫЙ КЛАСС
    public void putLetter() {
        System.out.println("\nМалыш пошел на кухню и оставил письмо, в котором написано:");
        class Paper {
            public void checkLetter() {
                System.out.println(Colors.ANSI_BLUE + letter + Colors.ANSI_RESET);
            }
        }
        Paper a = new Paper();
        a.checkLetter();
    }

    public Malish() {
        this.str = "Объект класса Малыш";
        this.dnaCode = 11;
    }

    public void getKnife() {
        System.out.println(Colors.ANSI_BLUE + "Малыш сходил на кухню и взял нож\n" + Colors.ANSI_RESET);
        knife = true;
    }

    public void getCoins(Kopilka kopilka, Karlson k, Wallet wallet) throws Exception {
        if (knife && kopilka.getCount() != 0) {

            int n = kopilka.getCount();

            for (int i = 0; i < n; i++) {
                wallet.coin_list.add(kopilka.getCoin());
                System.out.println("Малыш достал монету, номинал которой равен " + wallet.coin_list.get(i) + " Эре");

                if (wallet.coin_list.get(i).getInt() == k.getTrigger()) {
                    k.reaction();
                }

                this.money += wallet.coin_list.get(i).getInt();

                Thread.sleep(500);
            }
            System.out.println("----------------------------------------------------");
            System.out.println(Colors.ANSI_BLUE + "Малыш достал из копилки " + this.money + "\n" + Colors.ANSI_RESET);
        } else if (kopilka.getCount() == 0) {
            System.out.println(Colors.ANSI_RED + "Малыш уже достал все монеты из этой копилки\n" + Colors.ANSI_RESET);
        } else {
            System.out.println(Colors.ANSI_RED + "Малыш еще не взял нож для того, чтобы достать монеты из копилки\n" + Colors.ANSI_RESET);
        }
    }

    public int coinsCount() {
        if (this.money == 0) {
            return 0;
        } else {
            return money;
        }
    }

    public void buy(Shop sh, Wallet wallet, Pocket pocket, int saveCount) {
        System.out.println(Colors.ANSI_BLUE + "Малыш пошел в магазин" + Colors.ANSI_RESET);
        System.out.println("Малыш выбрал свои любимые сладости и пошел на кассу");
        System.out.println("----------------------------------------------------");

        // RuntimeException
        try {
            sh.buyFull(this, wallet, pocket, saveCount);
        } catch (ShopException e) {
            System.out.println(e.getMessage());
        }
    }

    public void goToKitchen(Table table, Vase vase) {
        System.out.println(Colors.ANSI_BLUE + "Малыш пришел на кухню, а там" + Colors.ANSI_RESET);
        table.checkFamily();
        System.out.println();
        System.out.println("Помимо этого на кухне стояла");


        vase.checkCandies();

        this.place = "Kitchen";
    }

    public void goToRoom() {
        System.out.println(Colors.ANSI_BLUE + "Малыш пришел в свою комнату, а там Карлсон" + Colors.ANSI_RESET);
        System.out.println();
        this.place = "Room";
    }

    public void diolog(Wallet wallet) {
        System.out.println("- Ты заставляешь меня так долго ждать! Меня, такого больного и несчастного, - с упреком сказал Карлсон.\n" +
                "- Я торопился как только мог, - оправдывался Малыш, - и столько всего накупил...\n" +
                "- И у тебя не осталось ни одной монетки? Я ведь должен получить пять эре за то, что меня будет кусать шарф! - испуганно перебил его Карлсон.\n");
        System.out.println("Малыш успокоил его, сказав, что приберег несколько монет:" + wallet.content());
    }

    public void thinkAboutMeet() {
        if (this.place == "Kitchen") {
            System.out.println(Colors.ANSI_PURPLE + "На мгновение Малышу в голову пришла мысль пригласить их всех к себе в комнату, " +
                    "\nчтобы познакомить наконец с Карлсоном. Однако, хорошенько подумав, он решил, что сегодня этого " +
                    "\nделать не стоит, - ведь они могут помешать ему отправиться с Карлсоном на крышу.\n" + Colors.ANSI_RESET);
        } else
            System.out.println(Colors.ANSI_RED + "\nМалыш еще не на кухне, чтобы думать о встрече\n" + Colors.ANSI_RESET);
    }

    public void getCookies(Vase vase, int n) {
        if (this.place == "Kitchen") {
            System.out.println("Мылыш хочет взять из вазы " + n + " Печенек");
            // ИСКЛЮЧЕНИЕ
            try {
                vase.getCookies(n);
            } catch (NoCookieException ex) {
                System.out.println(ex.getMessage());
            } finally {
                System.out.println();
            }
        } else
            System.out.println(Colors.ANSI_RED + "\nМалыш еще не на кухне, чтобы взять Печенье\n" + Colors.ANSI_RESET);
    }

    @Override
    public void thinkAbout(Thinkable somebody) {
        System.out.println(somebody.thinkFrom(this));
    }
}
