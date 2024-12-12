package com.example.demo.Levels;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.LevelController.LevelParent;
import com.example.demo.LevelController.LevelView;
import com.example.demo.Plane.EnemyPlane3;
import com.example.demo.Plane.UserPlane;
import javafx.scene.Scene;

/**
 * LevelThree represents the third level of the game, where the player faces waves of enemies
 * and needs to achieve a kill count to advance to the next level, which is the Boss level.
 */
public class LevelThree extends LevelParent {

    // Constants for the background image, next level, and gameplay parameters
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Level3BG.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelBoss"; // Transition to the Boss level
    private static final int TOTAL_ENEMIES = 9; // Number of enemies to spawn
    private static final int KILLS_TO_ADVANCE = 20; // Number of kills required to move to the next level
    private static final double ENEMY_SPAWN_PROBABILITY = 0.25; // Probability of spawning a general enemy
    private static final double ENEMY2_SPAWN_PROBABILITY = 0.15; // Probability of spawning a second type of enemy
    private static final double ENEMY3_SPAWN_PROBABILITY = 0.03; // Probability of spawning a third type of enemy (EnemyPlane3)

    // Player's initial health and plane speed
    private static final int PLAYER_INITIAL_HEALTH = 3; // Further reduced health for difficulty
    private static final double USER_PLANE_SPEED = 4.0; // Slower plane speed for challenge

    /**
     * Constructor to initialize the third level.
     *
     * @param screenHeight the height of the game screen
     * @param screenWidth  the width of the game screen
     */
    public LevelThree(double screenHeight, double screenWidth) {
        // Initialize the parent class with relevant parameters
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, "LEVEL 3: Defeat 20 enemies");
        this.currentLevelClassName = this.getClass().getName(); // Set the current level class name
//        initializeLevel();
    }

    /**
     * Check if the game is over by verifying if the player is destroyed or has met the kill target.
     */
    @Override
    protected void checkIfGameOver() {
        // If the user plane is destroyed, the game is lost
        if (userIsDestroyed()) {
            loseGame();
        }
        // If the user has reached the kill target, move to the next level (Boss level)
        else if (userHasReachedKillTarget()) {
            goToNextLevel(NEXT_LEVEL);
            System.out.println("Congratulations! You've completed Level 3.");
        }
    }

    /**
     * Initialize friendly units in the game, such as the player's plane.
     */
    @Override
    protected void initializeFriendlyUnits() {
        // Add the user plane to the root node
        getRoot().getChildren().add(getUser());

        // Bind the feature keys (e.g., movement, firing) to the user plane
        Scene scene = getRoot().getScene(); // Get the scene associated with the root
        if (scene != null) {
            bindFeatureKeys(scene, (UserPlane) getUser());
        } else {
            System.err.println("Scene not initialized. Ensure the root node is added to a scene.");
        }
    }

    /**
     * Spawn enemy units in the game based on spawn probabilities.
     */
    @Override
    protected void spawnEnemyUnits() {
        // Get the current number of enemies already spawned
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();

        // Spawn new enemies until the total enemy count reaches the maximum
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY3_SPAWN_PROBABILITY) { // Spawn EnemyPlane3 with a certain probability
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane3(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy); // Add the new enemy to the level
            }
        }
    }

    /**
     * Instantiate the level view for Level 3.
     *
     * @return a new LevelView instance
     */
    @Override
    protected LevelView instantiateLevelView() {
        // Set the collision sound for the level
        setCollisionSound("src/main/resources/lvlth.mp3");
        // Create a new LevelView with initial parameters (health and level)
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 2);
    }

    /**
     * Check if the user has reached the kill target to advance to the next level.
     *
     * @return true if the user has defeated enough enemies, false otherwise
     */
    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfHits() >= KILLS_TO_ADVANCE;
    }

    /**
     * Initialize the level. This method is used for any setup that needs to be done
     * when the level starts. Currently empty for Level 3.
     */
    @Override
    public void initializeLevel() {
        // Call the parent method with the current level name
        super.initializeLevel("3", 20,
                "       Power-Ups:\n\n" +
                        "      Press  1  For sheild Activation\n" +
                        "      Press  2  For Fire Boost. ");
    }
}
