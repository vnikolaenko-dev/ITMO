package attacks;

import ru.ifmo.se.pokemon.*;


public class Growl extends SpecialMove {
    public Growl(){
        super(Type.NORMAL, 0, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        Effect e = new Effect().chance(1).stat(Stat.ATTACK, -1);
        p.addEffect(e);
    }

    protected String describe(){
        return "использует Growl";
    }
}
