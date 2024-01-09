package se.itmo.victor;

import se.itmo.victor.lib.Entyties.Karlson;
import se.itmo.victor.lib.Entyties.Malish;
import se.itmo.victor.lib.Entyties.Dog;
import se.itmo.victor.lib.Items.*;
import se.itmo.victor.lib.Items.Sweety;

public class Main {
    public static void main(String[] args) throws Exception {

        Kopilka ob = new Kopilka(10);

        Malish malish = new Malish();
        Malish.Wallet wallet = malish.new Wallet();
        Malish.Pocket pocket = malish.new Pocket();

        Karlson karlson = new Karlson(15);
        Dog dog = new Dog();
        Shop shop = new Shop(Sweety.Ch, Sweety.LP, Sweety.NT);

        // 4

        Vase vase = new Vase(100);


        String[] family_names = {"Мама", "Папа", "Бенте", "Боссе"};
        Table table = new Table(family_names);
        // ---------------------------------------------------------------

        malish.thinkAbout(karlson);

        malish.getCoins(ob, karlson, wallet);
        malish.getKnife();
        malish.getCoins(ob, karlson, wallet);
        malish.getCoins(ob, karlson, wallet);

        malish.thinkAbout(dog);

        System.out.println("Перед походом в магазин у Малыша в кошелке:" + wallet.content());
        System.out.println("Перед походом в магазин у Малыша в крманах:" + pocket.content() + "\n");

        // UNCHEKED EXCEPTION
        malish.buy(shop, wallet, pocket, 3);
        // -----

        malish.thinkAbout(karlson);
        malish.thinkAbout(dog);

        // 4
        System.out.println("После похода в магазин у Малыша остались монеты:" + wallet.content());
        System.out.println("После похода в магазин у Малыша в крманах:" + pocket.content());
        malish.thinkAboutMeet();

        malish.goToKitchen(table, vase);

        // CHECKED EXCEPTION
        malish.getCookies(vase, 100);
        malish.getCookies(vase, 3);
        // -----------------

        malish.goToRoom();
        malish.diolog(wallet);

        malish.putLetter();

        Table.Guy guy1 = new Table.Guy("Паша");
        guy1.seat();
    }
}