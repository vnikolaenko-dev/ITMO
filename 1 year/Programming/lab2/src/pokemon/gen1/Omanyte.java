package pokemon.gen1;

import attacks.Blizzard;
import attacks.Bite;
import attacks.FocusEnergy;
import attacks.Growl;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Omanyte extends Pokemon {
    public Boolean isBoost = Boolean.FALSE;
    public Omanyte(String name, int lvl){
        super(name, lvl);
        this.setStats(35, 40, 100, 90, 55, 35);
        this.setType(Type.WATER);
        this.addType(Type.GROUND);

        Blizzard a1 = new Blizzard();
        this.addMove(a1);

        FocusEnergy a2 = new FocusEnergy();
        this.addMove(a2);

        Bite a3 = new Bite();
        this.addMove(a3);

        Growl a4 = new Growl();
        this.addMove(a4);
    }

    public void setBoost(){isBoost = Boolean.TRUE;}
}
