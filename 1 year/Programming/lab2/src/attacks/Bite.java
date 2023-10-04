package attacks;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class Bite extends SpecialMove {
    public Bite(){
        super(Type.DARK, 60, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.3){
            Effect.flinch(p);
        }
    }

    protected String describe(){
        return "использует Bite";
    }
}
