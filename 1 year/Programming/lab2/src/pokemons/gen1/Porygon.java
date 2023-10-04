package pokemons.gen1;

import attacks.CrossChop;
import attacks.HydroPump;
import attacks.KarateChop;
import attacks.MudSlap;
import ru.ifmo.se.pokemon.*;

public class Porygon extends Pokemon{
    public Porygon(String name, int level){
        super(name, level);
        this.setStats(65, 60, 70, 85, 75, 40);
        this.setType(Type.NORMAL);


        CrossChop a1 = new CrossChop();
        this.addMove(a1);

        HydroPump a2 = new HydroPump();
        this.addMove(a2);

        MudSlap a3 = new MudSlap();
        this.addMove(a3);

        KarateChop a4 = new KarateChop();
        this.addMove(a4);

    }
}
