// Done
package Game.Effects;

public class StunEffect extends StatusEffect{
    public StunEffect(){
        super("Stun", 2);
    }

    public boolean preventAction(){
        return true;
    }

    public boolean tickOnTurnEnd() {
        return true;
    }
}
