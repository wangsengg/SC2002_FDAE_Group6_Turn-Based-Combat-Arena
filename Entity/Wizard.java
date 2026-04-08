package Game.Entity;

import Game.SpecialSkill.ArcaneBlast;

public final class Wizard extends Player{
    public Wizard(){
        super("Wizard", 200, 50, 10, 20, new ArcaneBlast());
    }
}
