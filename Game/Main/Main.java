package Game.Main;

import java.util.ArrayList;
import java.util.List;

import Game.BattleEngine.BattleEngine;
import Game.BattleEngine.Difficulty;
import Game.BattleEngine.Level;
import Game.BattleEngine.LevelFactory;
import Game.BattleEngine.SpeedTurnOrder;
import Game.BattleEngine.TurnOrderStrategy;
import Game.Entity.Player;
import Game.Items.Item;
import Game.UI.GameCLI;
import Game.UI.GameUI;

public class Main {
    public static void main(String[] args) {
        GameUI ui = new GameCLI();

        boolean running = true;
        Player savedPlayer = null;
        Difficulty savedDifficulty = null;
        List<String> savedItemNames = new ArrayList<>();

        while (running) {
            ui.showWelcomeScreen();

            if (savedPlayer == null || savedDifficulty == null) {
                savedPlayer = ui.choosePlayer();
                ui.chooseStartingItems(savedPlayer);
                savedDifficulty = ui.chooseDifficulty();

                savedItemNames = extractItemNames(savedPlayer);
            } else {
                savedPlayer = recreatePlayer(savedPlayer, savedItemNames);
            }

            Level level = LevelFactory.createLevel(savedDifficulty);
            TurnOrderStrategy strategy = new SpeedTurnOrder();
            BattleEngine engine = new BattleEngine(savedPlayer, level, strategy, ui);

            engine.startBattle();
            ui.showBattleResult(engine.getBattleResult());

            int option = ui.choosePostGameOption();

            switch (option) {
                case 1:
                    savedPlayer = recreatePlayer(savedPlayer, savedItemNames);
                    break;
                case 2:
                    savedPlayer = null;
                    savedDifficulty = null;
                    savedItemNames.clear();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting game. Goodbye!");
                    break;
                default:
                    running = false;
                    break;
            }
        }
    }

    private static List<String> extractItemNames(Player player) {
        List<String> itemNames = new ArrayList<>();
        for (Item item : player.getItems()) {
            itemNames.add(item.getName());
        }
        return itemNames;
    }

    private static Player recreatePlayer(Player originalPlayer, List<String> itemNames) {
        Player newPlayer;

        if (originalPlayer.getName().equalsIgnoreCase("Warrior")) {
            newPlayer = new Game.Entity.Warrior();
        } else {
            newPlayer = new Game.Entity.Wizard();
        }

        for (String itemName : itemNames) {
            switch (itemName) {
                case "Potion":
                    newPlayer.addItem(new Game.Items.Potion());
                    break;
                case "Power Stone":
                    newPlayer.addItem(new Game.Items.PowerStone());
                    break;
                case "Smoke Bomb":
                    newPlayer.addItem(new Game.Items.SmokeBomb());
                    break;
            }
        }

        return newPlayer;
    }
}