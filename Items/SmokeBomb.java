package Game.Items;

import Game.BattleEngine.BattleEngine;
import Game.Effects.SmokeBombInvulnerabilityEffect;
import Game.Entity.Combatant;

public class SmokeBomb extends Item {
    public SmokeBomb() {
        super("Smoke Bomb");
    }

    public void use(Combatant actor, Combatant target, BattleEngine engine) {
        actor.applyEffect(new SmokeBombInvulnerabilityEffect());
    }
}
