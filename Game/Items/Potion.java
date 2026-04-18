package Game.Items;

import Game.BattleEngine.BattleEngine;
import Game.Entity.Combatant;
import Game.StatType.StatType;

public class Potion extends Item{
    public Potion(){
        super("Potion");
    }

    public void use(Combatant actor, Combatant target, BattleEngine engine){
        actor.changeStat(StatType.HP, 100);
    }
}
