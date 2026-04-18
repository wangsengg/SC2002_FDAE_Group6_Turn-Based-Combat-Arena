package Game.Items;

import Game.Entity.Combatant;
import Game.BattleEngine.BattleEngine;

public abstract class Item {
    protected String name;

    public Item(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public abstract void use(Combatant actor, Combatant target, BattleEngine engine);
}
