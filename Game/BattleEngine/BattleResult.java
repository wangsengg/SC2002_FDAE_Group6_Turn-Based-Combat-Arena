package Game.BattleEngine;

public class BattleResult {
    private boolean victory;
    private int remainingHP;
    private int totalRounds;
    private int enemiesRemaining;

    public BattleResult(boolean victory, int remainingHP, int totalRounds, int enemiesRemaining){
        this.victory = victory;
        this.remainingHP = remainingHP;
        this.totalRounds = totalRounds;
        this.enemiesRemaining = enemiesRemaining;
    }

    public boolean isVictory(){
        return victory;
    }

    public int getRemainingHP(){
        return remainingHP;
    }

    public int getTotalRounds(){
        return totalRounds;
    }

    public int getEnemiesRemaining(){
        return enemiesRemaining;
    }
}
