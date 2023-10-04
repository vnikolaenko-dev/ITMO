package pokemon.gen1;

import attacks.Bite;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Poochyena extends Pokemon {
    public Poochyena(String name, int lvl){
        super(name, lvl);
        this.setType(Type.DARK);
        this.setStats(35, 55, 35, 30, 30, 35);

        Bite a1 = new Bite();
        this.addMove(a1);
    }
}
