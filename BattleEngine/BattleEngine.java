package Game.BattleEngine;

import java.util.ArrayList;
import java.util.List;

import Game.Actions.BasicAttack;
import Game.Actions.Defend;
import Game.Actions.UseItem;
import Game.Entity.Combatant;
import Game.Entity.Enemy;
import Game.Entity.Player;
import Game.Items.Item;
import Game.Items.PowerStone;
import Game.UI.GameUI;

public class BattleEngine {
    protected Player player;
    protected List<Enemy> activeEnemies;
    protected TurnOrderStrategy turnOrderStrategy;
    protected Level level;
    protected int roundNumber;
    protected boolean backupSpawned;
    protected BattleResult battleResult;
    protected GameUI ui;

    public BattleEngine(Player player, Level level, TurnOrderStrategy turnOrderStrategy, GameUI ui) {
        this.player = player;
        this.level = level;
        this.turnOrderStrategy = turnOrderStrategy;
        this.activeEnemies = new ArrayList<>(level.getInitialEnemies());
        this.roundNumber = 1;
        this.backupSpawned = false;
        this.battleResult = null;
        this.ui = ui;
    }

    public void startBattle() {
        while (!checkBattleEnd()) {
            runRound();
        }

        boolean victory = player.isAlive();
        int remainingHP = player.isAlive() ? player.getCurrentHP() : 0;
        int enemiesRemaining = getAliveEnemies().size();

        battleResult = new BattleResult(victory, remainingHP, roundNumber - 1, enemiesRemaining);
        player.clearAllEffects();
    }

    public void runRound() {
        ui.displayRoundHeader(roundNumber);
        ui.displayBattleState(this);

        List<Combatant> participants = new ArrayList<>();

        if (player.isAlive()) {
            participants.add(player);
        }

        participants.addAll(getAliveEnemies());

        List<Combatant> orderedCombatants = turnOrderStrategy.determineOrder(participants);

        for (Combatant combatant : orderedCombatants) {
            if (checkBattleEnd()) {
                break;
            }

            if (!combatant.isAlive()) {
                continue;
            }

            processTurn(combatant);
        }

        applyRoundEffects();
        spawnBackup();

        ui.displayEndOfRoundState(this);

        player.clearRoundFlags();
        player.clearEffectRoundFlags();

        for (Enemy enemy : activeEnemies) {
            enemy.clearEffectRoundFlags();
        }

        roundNumber++;
    }

    public void processTurn(Combatant combatant) {
        if (!combatant.isAlive()) {
            return;
        }

        if (combatant instanceof Player) {
            Player currentPlayer = (Player) combatant;

            if (!currentPlayer.canAct()) {
                ui.showActionMessage(currentPlayer.getName() + " is stunned and cannot act this turn.");
                currentPlayer.updateTurnBasedEffects();
                flushMessages(currentPlayer);
                return;
            }

            handlePlayerTurn(currentPlayer);
            currentPlayer.getSkillCooldown().reduceAfterOwnerTurn();
            currentPlayer.updateTurnBasedEffects();
            flushMessages(currentPlayer);
        } else if (combatant instanceof Enemy) {
            Enemy enemy = (Enemy) combatant;

            if (!enemy.canAct()) {
                ui.showActionMessage(enemy.getName() + " is stunned and cannot act this turn.");
                enemy.updateTurnBasedEffects();
                flushMessages(enemy);
                return;
            }

            handleEnemyTurn(enemy);
            enemy.updateTurnBasedEffects();
            flushMessages(enemy);
            flushMessages(player);
        }

        removeDeadEnemies();
    }

    private void handlePlayerTurn(Player player) {
        boolean actionCompleted = false;

        while (!actionCompleted) {
            int choice = ui.choosePlayerAction(player);

            switch (choice) {
                case 1:
                    actionCompleted = handleBasicAttack(player);
                    break;
                case 2:
                    new Defend().execute(player, player, this);
                    ui.showActionMessage(player.getName() + " used Defend.");
                    flushMessages(player);
                    actionCompleted = true;
                    break;
                case 3:
                    actionCompleted = handleUseItem(player);
                    break;
                case 4:
                    actionCompleted = handleSpecialSkill(player);
                    break;
                default:
                    ui.showActionMessage("Invalid action.");
            }
        }
    }

    private boolean handleBasicAttack(Player player) {
        List<Enemy> aliveEnemies = getAliveEnemies();

        if (aliveEnemies.isEmpty()) {
            return false;
        }

        Enemy target = ui.chooseEnemyTarget(aliveEnemies);
        int beforeHP = target.getCurrentHP();

        new BasicAttack().execute(player, target, this);

        int damage = Math.max(0, beforeHP - target.getCurrentHP());
        ui.showActionMessage(player.getName() + " attacked " + target.getName()
                + " for " + damage + " damage.");
        flushMessages(target);

        return true;
    }

    private boolean handleUseItem(Player player) {
        if (player.getItems().isEmpty()) {
            ui.showActionMessage("No items available.");
            return false;
        }

        Item chosenItem = ui.chooseItem(player);
        if (chosenItem == null) {
            return false;
        }

        Combatant target = player;

        if (chosenItem instanceof PowerStone && !getAliveEnemies().isEmpty()) {
            target = ui.chooseEnemyTarget(getAliveEnemies());
        }

        new UseItem(chosenItem).execute(player, target, this);
        ui.showActionMessage(player.getName() + " used " + chosenItem.getName() + ".");
        flushMessages(player);
        if (target != player) {
            flushMessages(target);
        }
        flushEnemyMessages();
        return true;
    }

    private boolean handleSpecialSkill(Player player) {
        if (!player.getSkillCooldown().isAvailable()) {
            ui.showActionMessage("Special skill is on cooldown for "
                    + player.getDisplaySpecialSkillCooldown() + " more turn(s).");
            return false;
        }

        Combatant target = player;

        if (player.getSpecialSkill().requiresTargetSelection()) {
            if (!getAliveEnemies().isEmpty()) {
                target = ui.chooseEnemyTarget(getAliveEnemies());
            }
        }

        player.getSpecialSkill().execute(player, target, this);
        ui.showActionMessage(player.getName() + " used " + player.getSpecialSkill().getName() + ".");
        flushMessages(player);
        if (target != player) {
            flushMessages(target);
        }
        flushEnemyMessages();
        return true;
    }

    private void handleEnemyTurn(Enemy enemy) {
        if (!player.isAlive()) {
            return;
        }

        int beforeHP = player.getCurrentHP();
        new BasicAttack().execute(enemy, player, this);
        int damage = Math.max(0, beforeHP - player.getCurrentHP());

        ui.showActionMessage(enemy.getName() + " attacked " + player.getName()
                + " for " + damage + " damage.");
    }

    private void applyRoundEffects() {
        if (player.isAlive()) {
            player.updateRoundBasedEffects();
            flushMessages(player);
        }

        for (Enemy enemy : activeEnemies) {
            if (enemy.isAlive()) {
                enemy.updateRoundBasedEffects();
                flushMessages(enemy);
            }
        }

        removeDeadEnemies();
    }

    public boolean checkBattleEnd() {
        if (!player.isAlive()) {
            return true;
        }

        boolean noAliveEnemies = getAliveEnemies().isEmpty();
        boolean noBackupRemaining = !level.hasBackupSpawn() || backupSpawned;

        return noAliveEnemies && noBackupRemaining;
    }

    public void spawnBackup() {
        if (backupSpawned) {
            return;
        }

        if (getAliveEnemies().isEmpty() && level.hasBackupSpawn()) {
            activeEnemies.addAll(level.getBackupEnemies());
            backupSpawned = true;
            ui.showBackupSpawnMessage();
        }
    }

    private void flushMessages(Combatant combatant) {
        for (String message : combatant.drainBattleMessages()) {
            ui.showActionMessage(message);
        }
    }

    private void flushEnemyMessages() {
        for (Enemy enemy : activeEnemies) {
            flushMessages(enemy);
        }
    }

    public List<Enemy> getAliveEnemies() {
        List<Enemy> aliveEnemies = new ArrayList<>();

        for (Enemy enemy : activeEnemies) {
            if (enemy.isAlive()) {
                aliveEnemies.add(enemy);
            }
        }

        return aliveEnemies;
    }

    public BattleResult getBattleResult() {
        return battleResult;
    }

    private void removeDeadEnemies() {
        activeEnemies.removeIf(enemy -> !enemy.isAlive());
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

    public List<Enemy> getActiveEnemies() {
        return activeEnemies;
    }
}