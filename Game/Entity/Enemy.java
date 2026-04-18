package Game.Entity;

public abstract class Enemy extends Combatant{
    public Enemy(String name, int maxHP, int attack, int defense, int speed){
        super(name, maxHP, attack, defense, speed);
    }
}
