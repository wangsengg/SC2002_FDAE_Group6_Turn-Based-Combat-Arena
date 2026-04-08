package Game.BattleEngine;

import java.util.ArrayList;
import java.util.List;

import Game.Entity.Enemy;

public class Level {
    protected Difficulty difficulty;
    protected List<Enemy> initialEnemies;
    protected List<Enemy> backupEnemies;

    public Level(Difficulty difficulty, List<Enemy> initialEnemies, List<Enemy> backupEnemies){
        this.difficulty = difficulty;
        this.initialEnemies = new ArrayList<>(initialEnemies);
        this.backupEnemies = new ArrayList<>(backupEnemies);
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

    public List<Enemy> getInitialEnemies(){
        return new ArrayList<>(initialEnemies);
    }

    public List<Enemy> getBackupEnemies(){
        return new ArrayList<>(backupEnemies);
    }

    public boolean hasBackupSpawn(){
        return backupEnemies != null && !backupEnemies.isEmpty();
    }
}
