package Game.Actions;

import Game.BattleEngine.BattleEngine;
import Game.Entity.Combatant;
import Game.Entity.Player;

public abstract class SpecialSkill extends Action {
    protected String name;
    protected int coolDown;

    public SpecialSkill(String name, int coolDown) {
        this.name = name;
        this.coolDown = coolDown;
    }

    public String getName() {
        return name;
    }

    public int getCooldown() {
        return coolDown;
    }

    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        if (!(actor instanceof Player)) {
            throw new IllegalArgumentException("Special skills can only be used by players.");
        }

        Player player = (Player) actor;

        if (!player.getSkillCooldown().isAvailable()) {
            return;
        }

        applySkillEffect(actor, target, engine);
        player.getSkillCooldown().startCooldown(coolDown);
    }

    public boolean requiresTargetSelection() {
        return true;
    }

    public abstract void applySkillEffect(Combatant actor, Combatant target, BattleEngine engine);
}