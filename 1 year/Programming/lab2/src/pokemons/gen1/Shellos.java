package pokemons.gen1;

import attacks.HydroPump;
import attacks.ThunserShock;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Shellos extends Pokemon {
    public Shellos(String name, int lvl){
        super(name, lvl);
        this.setType(Type.WATER);
        this.setStats(76, 48, 48 ,56, 62, 34);

        ThunserShock a1 = new ThunserShock();
        this.addMove(a1);

        HydroPump a2 = new HydroPump();
        this.addMove(a2);
    }
}
