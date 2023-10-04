package pokemon.gen2;

import attacks.HydroPump;
import attacks.DragonRage;
import attacks.LightScreen;
import attacks.ThunserShock;
import pokemon.gen1.Aipom;
import ru.ifmo.se.pokemon.Type;

public class Ambipom extends Aipom {
    public Ambipom(String name, int lvl){
        super(name, lvl);
        this.setType(Type.NORMAL);
        this.setStats(75, 100, 66, 60, 65, 115);


        ThunserShock a1 = new ThunserShock();
        this.addMove(a1);

        HydroPump a2 = new HydroPump();
        this.addMove(a2);

        DragonRage a3 = new DragonRage();
        this.addMove(a3);

        LightScreen a4 = new LightScreen();
        this.addMove(a4);

    }


}
