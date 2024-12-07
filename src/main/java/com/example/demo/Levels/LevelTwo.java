package com.example.demo.Levels;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Plane.EnemyPlane2;
import com.example.demo.LevelController.LevelParent;
import com.example.demo.LevelController.LevelView;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Level2BG.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelThree";

    private static final int TOTAL_ENEMIES = 7;
    private static final int KILLS_TO_ADVANCE = 15;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.25;
    private static final int PLAYER_INITIAL_HEALTH = 4;

    public LevelTwo(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, "LEVEL TWO: Defeat 15 enemies");
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
                ActiveActorDestructible newEnemy = new EnemyPlane2(getScreenWidth(), newEnemyInitialYPosition);

                // Check for duplicates before adding
                if (!getRoot().getChildren().contains(newEnemy)) {
                    addEnemyUnit(newEnemy);
                    System.out.println("Added new enemy at Y position: " + newEnemyInitialYPosition);
                } else {
                    System.out.println("Duplicate enemy detected, skipping addition.");
                }
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
