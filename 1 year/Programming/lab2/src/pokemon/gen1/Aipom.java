package pokemon.gen1;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Aipom extends Pokemon {
    public Aipom(String name, int lvl){
        super(name, lvl);
        this.setType(Type.NORMAL);
        this.setStats(55, 70, 55, 40, 55, 85);

    }
}
