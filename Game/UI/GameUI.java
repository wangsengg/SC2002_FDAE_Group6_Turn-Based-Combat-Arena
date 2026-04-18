package Game.UI;

import java.util.List;

import Game.BattleEngine.BattleEngine;
import Game.BattleEngine.BattleResult;
import Game.BattleEngine.Difficulty;
import Game.Entity.Enemy;
import Game.Entity.Player;
import Game.Items.Item;

public interface GameUI {
    void showWelcomeScreen();

    Player choosePlayer();
    void chooseStartingItems(Player player);
    Difficulty chooseDifficulty();

    int choosePlayerAction(Player player);
    Enemy chooseEnemyTarget(List<Enemy> enemies);
    Item chooseItem(Player player);

    void displayRoundHeader(int roundNumber);
    void displayBattleState(BattleEngine engine);
    void displayEndOfRoundState(BattleEngine engine);

    void showActionMessage(String message);
    void showBackupSpawnMessage();
    void showBattleResult(BattleResult result);

    int choosePostGameOption();
}