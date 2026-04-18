package Game.SpecialSkill;

import java.util.List;

import Game.Actions.SpecialSkill;
import Game.BattleEngine.BattleEngine;
import Game.Effects.ArcaneBlastEffect;
import Game.Entity.Combatant;
import Game.Entity.Enemy;
import Game.StatType.StatType;

public class ArcaneBlast extends SpecialSkill {
    public ArcaneBlast() {
        super("Arcane Blast", 3);
    }

    public void applySkillEffect(Combatant actor, Combatant target, BattleEngine engine) {
        List<Enemy> enemies = engine.getAliveEnemies();

        for (Enemy enemy : enemies) {
            int damage = Math.max(0, actor.getAttack() - enemy.getDefense());
            enemy.changeStat(StatType.HP, -damage);

            if (!enemy.isAlive()) {
                actor.applyEffect(new ArcaneBlastEffect());
            }
        }
    }

    public boolean requiresTargetSelection(){
        return false;
    }
}