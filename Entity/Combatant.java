package Game.Entity;

import java.util.ArrayList;
import java.util.List;

import Game.Effects.StatusEffect;
import Game.StatType.StatType;

public abstract class Combatant {
    protected String name;
    protected int maxHP;
    protected int currentHP;
    protected int attack;
    protected int defense;
    protected int speed;
    protected List<StatusEffect> statusEffects;
    protected List<String> battleMessages;

    public Combatant(String name, int maxHP, int attack, int defense, int speed) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
        this.battleMessages = new ArrayList<>();
    }

    public void applyEffect(StatusEffect effect) {
        effect.onApply(this);
        effect.markAppliedThisRound();
        statusEffects.add(effect);

        if (effect.getRemainingTurns() < 0) {
            addBattleMessage(name + " gains status effect: " + effect.getName() + " (until end of level)");
        } else {
            addBattleMessage(name + " gains status effect: " + effect.getName()
                    + " (" + effect.getRemainingTurns() + " turn(s)/round(s))");
        }
    }

    public void updateTurnBasedEffects() {
        for (StatusEffect effect : statusEffects) {
            if (effect.tickOnTurnEnd()) {
                effect.onTurnEnd(this);
            }
        }
        removeExpiredEffects();
    }

    public void updateRoundBasedEffects() {
        for (StatusEffect effect : statusEffects) {
            if (effect.tickOnRoundEnd()) {
                effect.onRoundEnd(this);
            }
        }
        removeExpiredEffects();
    }

    public void clearAllEffects() {
        for (StatusEffect effect : statusEffects) {
            effect.onRemove(this);
        }
        statusEffects.clear();
    }

    public void clearEffectRoundFlags() {
        for (StatusEffect effect : statusEffects) {
            effect.clearRoundFlag();
        }
    }

    private void removeExpiredEffects() {
        List<StatusEffect> expiredEffects = new ArrayList<>();

        for (StatusEffect effect : statusEffects) {
            if (effect.isExpired()) {
                expiredEffects.add(effect);
            }
        }

        for (StatusEffect effect : expiredEffects) {
            effect.onRemove(this);
            addBattleMessage(name + "'s " + effect.getName() + " has expired.");
        }

        statusEffects.removeAll(expiredEffects);
    }

    public String getStatusEffectsDisplay() {
        if (statusEffects.isEmpty()) {
            return "None";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < statusEffects.size(); i++) {
            StatusEffect effect = statusEffects.get(i);

            sb.append(effect.getName());

            if (effect.getRemainingTurns() < 0) {
                sb.append(" (permanent)");
            } else {
                sb.append(" (").append(effect.getDisplayRemainingTurns()).append(")");
            }

            if (i < statusEffects.size() - 1) {
                sb.append(", ");
            }
        }

        return sb.toString();
    }

    public boolean canAct() {
        for (StatusEffect effect : statusEffects) {
            if (effect.preventAction()) {
                return false;
            }
        }
        return true;
    }

    public void changeStat(StatType stat, int amount) {
        switch (stat) {
            case ATTACK:
                attack = Math.max(0, attack + amount);
                break;

            case DEFENSE:
                defense = Math.max(0, defense + amount);
                break;

            case SPEED:
                speed = Math.max(0, speed + amount);
                break;

            case HP:
                if (amount < 0 && isInvulnerable()) {
                    addBattleMessage(name + " is invulnerable and takes no damage.");
                    break;
                }
                currentHP = Math.max(0, Math.min(maxHP, currentHP + amount));
                break;
        }
    }

    public boolean isInvulnerable() {
        for (StatusEffect effect : statusEffects) {
            if (effect.invulnerable()) {
                return true;
            }
        }
        return false;
    }

    protected void addBattleMessage(String message) {
        battleMessages.add(message);
    }

    public List<String> drainBattleMessages() {
        List<String> messages = new ArrayList<>(battleMessages);
        battleMessages.clear();
        return messages;
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getSpeed() {
        return speed;
    }
}