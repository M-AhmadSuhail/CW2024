package com.example.demo.Levels;

import com.example.demo.*;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Level2BG.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelThree";
    private static final int TOTAL_ENEMIES = 10; // Increased total enemies
    private static final int KILLS_TO_ADVANCE = 15;  // Higher kill requirement, adjust as necessary
    private static final double ENEMY_SPAWN_PROBABILITY = 0.45; // Increased spawn probability for regular enemies
    private static final double ENEMY2_SPAWN_PROBABILITY = 0.40; // Increased spawn probability for stronger enemies
    private static final int PLAYER_INITIAL_HEALTH = 4;
    private static final double USER_PLANE_SPEED = 5.0;

    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH,"LEVEL 2: Defeat 15 enemies");
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            goToNextLevel(NEXT_LEVEL);
            System.out.println("Going to next level");
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
            if (Math.random() < ENEMY2_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane2(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 2);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfHits() >= KILLS_TO_ADVANCE;
    }
}
