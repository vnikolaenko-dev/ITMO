package attacks;

import ru.ifmo.se.pokemon.*;

public class DragonRage extends PhysicalMove {
    public DragonRage(){
        super(Type.DRAGON, 1, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon p, double v) {
        super.applyOppDamage(p, 40);
    }

    protected String describe(){
        return "использует DragonRage";
    }
}
