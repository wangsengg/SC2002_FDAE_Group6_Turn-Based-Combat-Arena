package Game.Entity;

import Game.SpecialSkill.ShieldBash;

public final class Warrior extends Player{
    public Warrior(){
        super("Warrior", 260, 40, 20, 30, new ShieldBash());
    }
}
