package Game.Actions;

import Game.Entity.Combatant;
import Game.StatType.StatType;
import Game.BattleEngine.BattleEngine;

public class BasicAttack extends Action{
    public void execute(Combatant actor, Combatant target, BattleEngine engine){
        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        target.changeStat(StatType.HP, -damage);
    }
}
