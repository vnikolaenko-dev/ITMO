package attacks;

import ru.ifmo.se.pokemon.*;

public class LightScreen extends SpecialMove {
    public LightScreen(){
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect e = new Effect().turns(5).stat(Stat.SPECIAL_DEFENSE, +40);
        p.addEffect(e);
    }

    protected String describe(){
        return "использует LightScreen";
    }
}
