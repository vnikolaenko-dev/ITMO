package se.itmo.victor;

import se.itmo.victor.lib.PR.Karlson;
import se.itmo.victor.lib.PR.Malish;
import se.itmo.victor.lib.PR.Dog;
import se.itmo.victor.lib.SH.Coin;
import se.itmo.victor.lib.SH.Kopilka;
import se.itmo.victor.lib.SH.Shop;
import se.itmo.victor.lib.SW.Sweety;

public class Main{
    public static void main(String[] args) throws Exception {

        Kopilka ob = new Kopilka(50);
        Malish malish = new Malish();
        Karlson karlson = new Karlson(15);
        Dog dog= new Dog();
        Shop shop = new Shop(Sweety.Ch, Sweety.LP, Sweety.NT); // по возрастаницию цены

        

        malish.thinkAbout(karlson);

        malish.getCoins(ob, karlson);
        malish.getKnife();
        malish.getCoins(ob, karlson);
        malish.getCoins(ob, karlson);

        malish.thinkAbout(dog);

        malish.buy(shop);

        malish.thinkAbout(karlson);
        malish.thinkAbout(dog);
    }
}