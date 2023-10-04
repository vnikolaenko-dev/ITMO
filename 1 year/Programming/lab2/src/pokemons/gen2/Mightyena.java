package pokemons.gen2;

import attacks.Blizzard;
import attacks.FocusEnergy;
import pokemons.gen1.Poochyena;
import ru.ifmo.se.pokemon.Type;

public class Mightyena extends Poochyena {
    public Mightyena(String name, int level){
        super(name, level);
        this.setStats(70, 90, 70, 60, 60, 70);
        this.setType(Type.DARK);


        Blizzard a1 = new Blizzard();
        this.addMove(a1);

        FocusEnergy a2 = new FocusEnergy();
        this.addMove(a2);
    }
}
