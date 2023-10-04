package pokemon.gen2;

import attacks.DragonRage;
import pokemon.gen1.Shellos;
import ru.ifmo.se.pokemon.Type;

public class Gastrodon extends Shellos {
    public Gastrodon(String name, int lvl){
        super(name, lvl);
        this.addType(Type.GROUND);
        this.setStats(111, 83, 68, 92, 82, 39);

        DragonRage a = new DragonRage();
        this.addMove(a);
    }
}
