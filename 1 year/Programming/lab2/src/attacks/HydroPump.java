package attacks;

import ru.ifmo.se.pokemon.*;

public class HydroPump extends PhysicalMove {
    public HydroPump(){
        super(Type.WATER, 120, 80);
    }

    protected String describe(){
        return "использует Hydro_Pump";
    }
}
