package Game.BattleEngine;

import java.util.List;
import Game.Entity.Combatant;

public interface TurnOrderStrategy {
    List<Combatant> determineOrder(List<Combatant> combatants);
}
