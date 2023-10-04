package attacks;

import ru.ifmo.se.pokemon.*;

public class FocusEnergy extends SpecialMove {
    public FocusEnergy(){
        super(Type.NORMAL, 0, 100);
    }


    protected String describe(){
        return "использует Focus_Energy";
    }
}
