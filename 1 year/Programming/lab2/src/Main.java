import pokemons.gen1.Omanyte;
import pokemons.gen1.Porygon;
import pokemons.gen1.Shellos;
import pokemons.gen2.Ambipom;
import pokemons.gen2.Gastrodon;
import pokemons.gen2.Mightyena;

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
