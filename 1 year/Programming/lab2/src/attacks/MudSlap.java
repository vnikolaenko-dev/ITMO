package attacks;

import ru.ifmo.se.pokemon.*;

public class MudSlap extends SpecialMove {
    public MudSlap(){
        super(Type.GROUND, 20, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p){
        Effect e = new Effect().chance(1).turns(3).stat(Stat.ATTACK, +1);
        p.addEffect(e);
    }

    protected String describe(){
        return "использует Mud_slap";
    }
}
