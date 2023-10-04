package attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class KarateChop extends PhysicalMove {
    public KarateChop(){
        super(Type.FIGHTING, 50, 100);
    }

    protected String describe(){
        return "использует Karate_Chop";
    }
}
