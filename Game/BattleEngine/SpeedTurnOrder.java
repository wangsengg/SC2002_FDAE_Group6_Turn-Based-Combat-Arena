package Game.BattleEngine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Game.Entity.Combatant;

public class SpeedTurnOrder implements TurnOrderStrategy{
    public List<Combatant> determineOrder(List<Combatant> combatants){
        List<Combatant> ordered = new ArrayList<>(combatants);
        ordered.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return ordered;
    }
}
