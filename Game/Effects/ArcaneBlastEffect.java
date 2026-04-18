package Game.Effects;

import Game.Entity.Combatant;
import Game.StatType.StatType;

public class ArcaneBlastEffect extends StatusEffect{
    public ArcaneBlastEffect(){
        super("Arcane Blast ATK Buff", -1);
    }

    public void onApply(Combatant target){
        target.changeStat(StatType.ATTACK, 10);
    }

    // Use this to clear the stats effect array once the level ends
    public void onRemove(Combatant target){
        target.changeStat(StatType.ATTACK, -10);
    }
}
