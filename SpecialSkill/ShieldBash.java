package Game.SpecialSkill;

import Game.Actions.SpecialSkill;
import Game.BattleEngine.BattleEngine;
import Game.Effects.StunEffect;
import Game.Entity.Combatant;
import Game.StatType.StatType;

public class ShieldBash extends SpecialSkill {
    public ShieldBash() {
        super("Shield Bash", 3);
    }

    public void applySkillEffect(Combatant actor, Combatant target, BattleEngine engine) {
        int damage = Math.max(0, actor.getAttack() - target.getDefense());
        target.changeStat(StatType.HP, -damage);

        if (target.isAlive()) {
            target.applyEffect(new StunEffect());
        }
    }
}