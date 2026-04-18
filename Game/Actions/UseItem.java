package Game.Actions;

import Game.BattleEngine.BattleEngine;
import Game.Entity.Combatant;
import Game.Entity.Player;
import Game.Items.Item;

public class UseItem extends Action {
    protected Item item;

    public UseItem(Item item) {
        this.item = item;
    }

    public void execute(Combatant actor, Combatant target, BattleEngine engine) {
        item.use(actor, target, engine);

        if (actor instanceof Player) {
            ((Player) actor).removeItem(item);
        }
    }
}