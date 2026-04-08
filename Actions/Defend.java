package Game.Actions;

import Game.Entity.Combatant;
import Game.Effects.DefendEffect;
import Game.BattleEngine.BattleEngine;

public class Defend extends Action{
    public void execute(Combatant actor, Combatant target, BattleEngine engine){
        actor.applyEffect(new DefendEffect());   
    }
}
