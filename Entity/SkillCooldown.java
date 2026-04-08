package Game.Entity;

public class SkillCooldown {
    private int turnsRemaining;
    private boolean usedThisRound;

    public SkillCooldown() {
        this.turnsRemaining = 0;
        this.usedThisRound = false;
    }

    public boolean isAvailable() {
        return turnsRemaining == 0;
    }

    public void startCooldown(int turns) {
        this.turnsRemaining = turns;
        this.usedThisRound = true;
    }

    public void reduceAfterOwnerTurn() {
        if (turnsRemaining > 0) {
            turnsRemaining--;
        }
    }

    public int getCurrentTurnsRemaining() {
        return turnsRemaining;
    }

    public int getEndOfRoundDisplayValue() {
        if (usedThisRound) {
            return turnsRemaining + 1;
        }
        return turnsRemaining;
    }

    public void clearRoundFlag() {
        usedThisRound = false;
    }
}