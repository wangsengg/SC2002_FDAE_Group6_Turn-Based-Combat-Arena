package Game.Effects;

import Game.Entity.Combatant;

public abstract class StatusEffect {
    protected String name;
    protected int remainingTurns;
    protected boolean appliedThisRound;

    public StatusEffect(String name, int remainingTurns) {
        this.name = name;
        this.remainingTurns = remainingTurns;
        this.appliedThisRound = false;
    }

    public void onTurnStart(Combatant target) {
    }

    public void onTurnEnd(Combatant target) {
        if (remainingTurns > 0) {
            remainingTurns--;
        }
    }

    public void onRoundEnd(Combatant target) {
        if (remainingTurns > 0) {
            remainingTurns--;
        }
    }

    public boolean tickOnTurnEnd() {
        return false;
    }

    public boolean tickOnRoundEnd() {
        return false;
    }

    public void onApply(Combatant target) {
    }

    public void onRemove(Combatant target) {
    }

    public boolean isExpired() {
        return remainingTurns == 0;
    }

    public boolean preventAction() {
        return false;
    }

    public boolean invulnerable() {
        return false;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public String getName() {
        return name;
    }

    public void markAppliedThisRound() {
        appliedThisRound = true;
    }

    public void clearRoundFlag() {
        appliedThisRound = false;
    }

    public int getDisplayRemainingTurns() {
        if (remainingTurns < 0) {
            return remainingTurns;
        }

        if (tickOnTurnEnd() && appliedThisRound) {
            return remainingTurns + 1;
        }

        return remainingTurns;
    }
}
