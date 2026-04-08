package Game.Entity;

import java.util.ArrayList;
import java.util.List;

import Game.Actions.SpecialSkill;
import Game.Items.Item;

public abstract class Player extends Combatant {
    protected List<Item> items;
    protected SpecialSkill specialSkill;
    protected SkillCooldown skillCooldown;

    public Player(String name, int maxHP, int attack, int defense, int speed, SpecialSkill specialSkill) {
        super(name, maxHP, attack, defense, speed);
        this.specialSkill = specialSkill;
        this.items = new ArrayList<>();
        this.skillCooldown = new SkillCooldown();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }

    public SkillCooldown getSkillCooldown() {
        return skillCooldown;
    }

    public int getDisplaySpecialSkillCooldown() {
        return skillCooldown.getCurrentTurnsRemaining();
    }

    public int getEndOfRoundSpecialSkillCooldown() {
        return skillCooldown.getEndOfRoundDisplayValue();
    }

    public void clearRoundFlags() {
        skillCooldown.clearRoundFlag();
    }
}
