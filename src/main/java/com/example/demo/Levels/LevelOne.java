package com.example.demo.Levels;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Plane.EnemyPlane;
import com.example.demo.LevelController.LevelParent;
import com.example.demo.LevelController.LevelView;

import java.net.URL;

/**
 * Represents the first level of the game.
 * The objective of this level is to defeat 10 enemies to advance to the next level.
 */
public class LevelOne extends LevelParent {

    /**
     * The name of the background image for Level 1.
     */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Level1BG1.jpg";

    /**
     * The custom message to appear on screen when user first enters level.
     */
    private static final String MESSAGE_ON_SCREEN = "LEVEL ONE: Defeat 10 enemies";
    /**
     * The class name of the next level to load.
     */
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelTwo";

    /**
     * The total number of enemies that can spawn in this level.
     */
    private static final int TOTAL_ENEMIES = 5;

    /**
     * The number of kills required to advance to the next level.
     */
    private static final int KILLS_TO_ADVANCE = 10;

    /**
     * The probability of spawning a new enemy.
     */
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;

    /**
     * The initial health of the player.
     */
    private static final int PLAYER_INITIAL_HEALTH = 5;

    /**
     * A flag to track whether the level is transitioning to the next level.
     */
    private boolean transitioning = false;

    /**
     * Constructor to initialize the first level.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth The width of the screen.
     */
    public LevelOne(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, "LEVEL 1: Defeat 10 enemies");
        this.currentLevelClassName = this.getClass().getName();
    }

    /**
     * Checks if the game is over by evaluating the player's status.
     * If the player is destroyed, the game ends with a loss.
     * If the player reaches the kill target, the game transitions to the next level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (userHasReachedKillTarget()) {
            goToNextLevel(NEXT_LEVEL);
            System.out.println("Going to next level");

        }
    }

    /**
     * Initializes friendly units (the player) on the screen.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns enemy units based on the spawn probability and the maximum number of enemies.
     * New enemies are spawned at a random Y position within the screen's vertical bounds.
     */
    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        System.out.println("Current enemies: " + currentNumberOfEnemies + "/" + TOTAL_ENEMIES);

        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
                System.out.println("Spawned new enemy at Y position: " + newEnemyInitialYPosition);
            }
        }
    }

    /**
     * Instantiates the view for this level.
     *
     * @return The level view.
     */
    @Override
    protected LevelView instantiateLevelView() {

        setCollisionSound("src/main/resources/lvlb.mp3");
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 1);
    }

    /**
     * Displays an entry message on the screen when the level starts.
     * The message shows the current level number and the number of kills required to advance.
     */
    /**
     * Checks if the user has reached the kill target for this level.
     *
     * @return true if the user has reached the required number of kills, otherwise false.
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfHits() >= KILLS_TO_ADVANCE;
    }


    @Override
    protected void initializeLevel() {
   //  setCollisionSound("src/main/resources/sounds/default.mp3");
        // Other initialization logic
    }

}
