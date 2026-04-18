package Game.BattleEngine;

import java.util.ArrayList;
import java.util.List;
import Game.Entity.Enemy;
import Game.Entity.Wolf;
import Game.Entity.Goblin;

public class LevelFactory {
    public static Level createLevel(Difficulty difficulty){
        List<Enemy> initialEnemies = new ArrayList<>();
        List<Enemy> backupEnemies = new ArrayList<>();

        switch (difficulty){
            case EASY:
                initialEnemies.add(new Goblin());
                initialEnemies.add(new Goblin());
                initialEnemies.add(new Goblin());
                break;

            case MEDIUM:
                initialEnemies.add(new Goblin());
                initialEnemies.add(new Wolf());
                backupEnemies.add(new Wolf());
                backupEnemies.add(new Wolf());
                break;
            
                case HARD:
                    initialEnemies.add(new Goblin());
                    initialEnemies.add(new Goblin());
                    backupEnemies.add(new Goblin());
                    backupEnemies.add(new Wolf());
                    backupEnemies.add(new Wolf());
                    break;
        }

        return new Level(difficulty, initialEnemies, backupEnemies);
    }
}
