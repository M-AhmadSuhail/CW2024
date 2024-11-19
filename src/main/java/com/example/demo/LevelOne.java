package com.example.demo;

import javafx.scene.Group;

public class LevelOne extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
    private static final int TOTAL_ENEMIES = 5;
    private static final int KILLS_TO_ADVANCE = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    private boolean transitioning = false;  // Add this variable to track the transition state

    public LevelOne(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    public void initializeGame(Group root) {
        // Additional setup for the level (without DisplayKills)
    }

    @Override
    protected void checkIfGameOver() {
        int currentKills = getUser().getNumberOfKills();
        System.out.println("Checking if game is over. Current kills: " + currentKills);

        // Check for player destruction (game over)
        if (userIsDestroyed()) {
            loseGame();
        }
        // Check if user has reached the kill target
        else if (!transitioning && currentKills >= KILLS_TO_ADVANCE) {
            transitioning = true; // Prevent further transitions
            System.out.println("User reached kill target. Transitioning to next level...");
            goToNextLevel("com.example.demo.LevelTwo"); // Call the inherited method to transition
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 1);
    }

    private boolean userHasReachedKillTarget() {
        int kills = getUser().getNumberOfKills();
        System.out.println("Current kill count: " + kills);
        return kills >= KILLS_TO_ADVANCE;  // Ensure this compares with the correct target kill count
    }
}
