package attacks;

import ru.ifmo.se.pokemon.*;

public class CrossChop extends PhysicalMove {
    public CrossChop(){
        super(Type.FIGHTING, 100, 80);
    }

    protected String describe(){
        return "использует Cross_Chop";
    }
}
