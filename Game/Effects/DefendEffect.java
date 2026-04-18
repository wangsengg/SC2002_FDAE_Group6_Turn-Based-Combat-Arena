// Done
package Game.Effects;

import Game.Entity.Combatant;
import Game.StatType.StatType;

public class DefendEffect extends StatusEffect{
    public DefendEffect(){
        super("Defend", 2);
    }

    public void onApply(Combatant target){
        target.changeStat(StatType.DEFENSE, 10);
    }

    public void onRemove(Combatant target){
        target.changeStat(StatType.DEFENSE, -10);
    }

    public boolean tickOnRoundEnd(){
        return true;
    }
}
