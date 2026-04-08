package Game.Actions;

import Game.Entity.Combatant;
import Game.BattleEngine.BattleEngine;

public abstract class Action{
    public abstract void execute(Combatant actor, Combatant target, BattleEngine engine);
}
