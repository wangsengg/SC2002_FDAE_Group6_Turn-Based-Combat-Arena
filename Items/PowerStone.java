package Game.Items;

import Game.BattleEngine.BattleEngine;
import Game.Entity.Combatant;
import Game.Entity.Player;

public class PowerStone extends Item {
    public PowerStone() {
        super("Power Stone");
    }

    public void use(Combatant actor, Combatant target, BattleEngine engine) {
        if (!(actor instanceof Player)) {
            throw new IllegalArgumentException("Power Stone can only be used by players.");
        }

        Player player = (Player) actor;
        player.getSpecialSkill().applySkillEffect(actor, target, engine);
    }
}
