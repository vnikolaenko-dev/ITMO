import pokemon.gen1.Omanyte;
import pokemon.gen1.Porygon;
import pokemon.gen1.Shellos;
import pokemon.gen2.Ambipom;
import pokemon.gen2.Gastrodon;
import pokemon.gen2.Mightyena;

import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args){

        Porygon p1 = new Porygon("Android", 35);
        Mightyena p2 = new Mightyena("MacOS", 35);
        Omanyte p3 = new Omanyte("Windows", 35);
        Shellos p4 = new Shellos("Linux", 35);
        Gastrodon p5 = new Gastrodon("Rose", 47);
        Ambipom p6 = new Ambipom("IOS", 35);

        Battle battle = new Battle();
        battle.addAlly(p1);
        battle.addAlly(p3);
        battle.addAlly(p5);

        battle.addFoe(p2);
        battle.addFoe(p4);
        battle.addFoe(p6);
        battle.go();
    }
}
