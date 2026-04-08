package Game.UI;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Game.BattleEngine.BattleEngine;
import Game.BattleEngine.BattleResult;
import Game.BattleEngine.Difficulty;
import Game.Entity.Enemy;
import Game.Entity.Player;
import Game.Entity.Warrior;
import Game.Entity.Wizard;
import Game.Items.Item;
import Game.Items.Potion;
import Game.Items.PowerStone;
import Game.Items.SmokeBomb;

public class GameCLI implements GameUI {
    private final Scanner scanner;

    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcomeScreen() {
        System.out.println("========================================");
        System.out.println("     TURN-BASED COMBAT ARENA");
        System.out.println("========================================");
        System.out.println();
    }

    public Player choosePlayer() {
        while (true) {
            System.out.println("Choose your player:");
            System.out.println("1. Warrior  | HP: 260  ATK: 40  DEF: 20  SPD: 30");
            System.out.println("   Special Skill: Shield Bash");
            System.out.println("2. Wizard   | HP: 200  ATK: 50  DEF: 10  SPD: 20");
            System.out.println("   Special Skill: Arcane Blast");
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    return new Warrior();
                case 2:
                    return new Wizard();
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    public void chooseStartingItems(Player player) {
        System.out.println();
        System.out.println("Choose 2 starting items. Duplicates are allowed.");
        System.out.println("1. Potion");
        System.out.println("2. Power Stone");
        System.out.println("3. Smoke Bomb");

        for (int i = 1; i <= 2; i++) {
            while (true) {
                System.out.print("Choose item " + i + ": ");
                int choice = readInt();

                Item item = null;
                switch (choice) {
                    case 1:
                        item = new Potion();
                        break;
                    case 2:
                        item = new PowerStone();
                        break;
                    case 3:
                        item = new SmokeBomb();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

                player.addItem(item);
                System.out.println(item.getName() + " added.");
                break;
            }
        }
        System.out.println();
    }

    public Difficulty chooseDifficulty() {
        while (true) {
            System.out.println("Choose difficulty:");
            System.out.println("1. EASY   - Initial: 3 Goblins");
            System.out.println("2. MEDIUM - Initial: 1 Goblin, 1 Wolf | Backup: 2 Wolves");
            System.out.println("3. HARD   - Initial: 2 Goblins | Backup: 1 Goblin, 2 Wolves");
            System.out.print("Enter choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    return Difficulty.EASY;
                case 2:
                    return Difficulty.MEDIUM;
                case 3:
                    return Difficulty.HARD;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    public int choosePlayerAction(Player player) {
        while (true) {
            System.out.println();
            System.out.println("Choose action:");
            System.out.println("1. Basic Attack");
            System.out.println("2. Defend");
            System.out.println("3. Use Item");
            System.out.println("4. Special Skill (" + player.getSpecialSkill().getName()
                    + ") | Cooldown: " + player.getDisplaySpecialSkillCooldown());
            System.out.print("Enter choice: ");

            int choice = readInt();
            if (choice >= 1 && choice <= 4) {
                return choice;
            }

            System.out.println("Invalid choice. Please try again.");
        }
    }

    public Enemy chooseEnemyTarget(List<Enemy> enemies) {
        while (true) {
            System.out.println();
            System.out.println("Choose target:");

            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                System.out.println((i + 1) + ". " + enemy.getName()
                        + " | HP: " + enemy.getCurrentHP()
                        + " | DEF: " + enemy.getDefense()
                        + " | SPD: " + enemy.getSpeed());
            }

            System.out.print("Enter choice: ");
            int choice = readInt();

            if (choice >= 1 && choice <= enemies.size()) {
                return enemies.get(choice - 1);
            }

            System.out.println("Invalid target. Please try again.");
        }
    }

    public Item chooseItem(Player player) {
        List<Item> items = player.getItems();

        if (items.isEmpty()) {
            return null;
        }

        while (true) {
            System.out.println();
            System.out.println("Choose item:");

            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i).getName());
            }

            System.out.print("Enter choice: ");
            int choice = readInt();

            if (choice >= 1 && choice <= items.size()) {
                return items.get(choice - 1);
            }

            System.out.println("Invalid item choice. Please try again.");
        }
    }

    public void displayRoundHeader(int roundNumber) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("Round " + roundNumber);
        System.out.println("========================================");
    }

    public void displayBattleState(BattleEngine engine) {
        Player player = engine.getPlayer();

        System.out.println();
        System.out.println("----- Player -----");
        System.out.println(player.getName()
                + " | HP: " + player.getCurrentHP() + "/" + player.getMaxHP()
                + " | ATK: " + player.getAttack()
                + " | DEF: " + player.getDefense()
                + " | SPD: " + player.getSpeed());

        System.out.print("Items: ");
        if (player.getItems().isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < player.getItems().size(); i++) {
                System.out.print(player.getItems().get(i).getName());
                if (i < player.getItems().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }

        System.out.println("Special Skill Cooldown: " + player.getDisplaySpecialSkillCooldown());
        System.out.println("Status Effects: " + player.getStatusEffectsDisplay());

        System.out.println();
        System.out.println("----- Enemies -----");
        List<Enemy> enemies = engine.getAliveEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            System.out.println((i + 1) + ". " + enemy.getName()
                    + " | HP: " + enemy.getCurrentHP()
                    + " | ATK: " + enemy.getAttack()
                    + " | DEF: " + enemy.getDefense()
                    + " | SPD: " + enemy.getSpeed()
                    + " | Effects: " + enemy.getStatusEffectsDisplay());
        }
        System.out.println();
    }

    public void showActionMessage(String message) {
        System.out.println(message);
    }

    public void showBackupSpawnMessage() {
        System.out.println();
        System.out.println("Backup enemies have appeared!");
    }

    public void showBattleResult(BattleResult result) {
        System.out.println();
        System.out.println("========================================");
        if (result.isVictory()) {
            System.out.println("Congratulations, you have defeated all your enemies.");
            System.out.println("Remaining HP: " + result.getRemainingHP());
            System.out.println("Total Rounds: " + result.getTotalRounds());
        } else {
            System.out.println("Defeated. Don't give up, try again!");
            System.out.println("Enemies Remaining: " + result.getEnemiesRemaining());
            System.out.println("Total Rounds Survived: " + result.getTotalRounds());
        }
        System.out.println("========================================");
        System.out.println();
    }

    public int choosePostGameOption() {
        while (true) {
            System.out.println("What would you like to do next?");
            System.out.println("1. Replay with same settings");
            System.out.println("2. Start a new game");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = readInt();
            if (choice >= 1 && choice <= 3) {
                return choice;
            }

            System.out.println("Invalid choice. Please try again.");
        }
    }

    public void displayEndOfRoundState(BattleEngine engine) {
        Player player = engine.getPlayer();

        System.out.print("End of Round " + engine.getRoundNumber() + ": ");
        System.out.print(player.getName() + " HP: " + player.getCurrentHP() + "/" + player.getMaxHP() + " | ");
        System.out.print("Effects: " + player.getStatusEffectsDisplay() + " | \n");

        List<Enemy> enemies = engine.getAliveEnemies();
        for (Enemy enemy : enemies) {
            System.out.println(enemy.getName() + " HP: " + enemy.getCurrentHP()
                    + " | Effects: " + enemy.getStatusEffectsDisplay() + " | ");
        }

        int potionCount = 0;
        int smokeBombCount = 0;
        int powerStoneCount = 0;

        for (Item item : player.getItems()) {
            if (item.getName().equals("Potion")) {
                potionCount++;
            } else if (item.getName().equals("Smoke Bomb")) {
                smokeBombCount++;
            } else if (item.getName().equals("Power Stone")) {
                powerStoneCount++;
            }
        }

        System.out.print("Potion: " + potionCount + " | ");
        System.out.print("Smoke Bomb: " + smokeBombCount + " | ");
        System.out.print("Power Stone: " + powerStoneCount + " | ");
        System.out.println("Special Skill Cooldown: " + player.getEndOfRoundSpecialSkillCooldown() + " round(s)");
    }

    private int readInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}