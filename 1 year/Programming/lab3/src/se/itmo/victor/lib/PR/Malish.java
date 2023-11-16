package se.itmo.victor.lib.PR;

import se.itmo.victor.lib.SH.*;
import se.itmo.victor.lib.System.Colors;
public class Malish extends Entity implements Thinking{
    protected Coin[] sp;
    protected int money = 0;
    protected boolean knife = false;

    public Malish() {
        this.str= "Объект класса Малыш";
        this.dnaCode = 11;
    }

    public void getKnife() {
        System.out.println(Colors.ANSI_BLUE + "Малыш сходил на кухню и взял нож\n" + Colors.ANSI_RESET);
        knife = true;
    }

    public void getCoins(Kopilka kopilka, Karlson k) throws Exception{
        if (knife && kopilka.getCount() != 0) {
            this.sp = new Coin[kopilka.getCount()];
            int n = kopilka.getCount();

            for (int i = 0; i < n; i++) {
                this.sp[i] = kopilka.getCoin();
                System.out.println("Малыш достал монету, номинал которой равен " + sp[i] + " Эре");

                if (sp[i].getInt() == k.getTrigger()) {
                    k.reaction();
                }

                this.money += this.sp[i].getInt();

                Thread.sleep(500);
            }
            System.out.println("----------------------------------------------------");
            System.out.println(Colors.ANSI_BLUE + "Малыш достал из копилки " + this.money + "\n" + Colors.ANSI_RESET);
        }
        else if (kopilka.getCount() == 0){
            System.out.println(Colors.ANSI_RED + "Малыш уже достал все монеты из этой копилки\n" + Colors.ANSI_RESET);
        }
        else {
            System.out.println(Colors.ANSI_RED + "Малыш еще не взял нож для того, чтобы достать монеты из копилки\n" + Colors.ANSI_RESET);
        }
    }

    public int coinsCount() {
        if (this.sp == null){
            return 0;
        }
        else {
            return this.sp.length;
        }
    }

    public void buy(Shop sh) {
        System.out.println(Colors.ANSI_BLUE + "Малыш пошел в магазин" + Colors.ANSI_RESET);
        System.out.println("Малыш выбрал свои любимые сладости и пошел на кассу");
        System.out.println("----------------------------------------------------");
        sh.buyFull(this.money, sh);
        this.money = 0;
        this.sp = null;
    }

    @Override
    public void thinkAbout(Thinkable somebody) {
        System.out.println(somebody.thinkFrom(this));
    }
}
