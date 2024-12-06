package com.example.demo.Levels;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.LevelController.LevelParent;
import com.example.demo.LevelController.LevelView;
import com.example.demo.Plane.EnemyPlane;
import com.example.demo.Plane.EnemyPlane2;
import com.example.demo.Plane.EnemyPlane3;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Level3BG.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelBoss"; // Assuming a game end screen
    private static final int TOTAL_ENEMIES = 15; // More enemies
    private static final int KILLS_TO_ADVANCE = 20; // Higher kill requirement
    private static final double ENEMY_SPAWN_PROBABILITY = 0.25; // Higher enemy spawn probability
    private static final double ENEMY2_SPAWN_PROBABILITY = 0.15;
    private static final double ENEMY3_SPAWN_PROBABILITY = .05;

    private static final int PLAYER_INITIAL_HEALTH = 3; // Further reduced health
    private static final double USER_PLANE_SPEED = 4.0; // Slower plane speed

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, "LEVEL 3: Defeat 20 enemies");
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (userHasReachedKillTarget()) {
            goToNextLevel(NEXT_LEVEL);
            System.out.println("Congratulations! You've completed the game.");
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
        if (Math.random() < ENEMY3_SPAWN_PROBABILITY) {
            double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
            ActiveActorDestructible newEnemy = new EnemyPlane3(getScreenWidth(), newEnemyInitialYPosition);
            addEnemyUnit(newEnemy);
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 3);
    }

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfHits() >= KILLS_TO_ADVANCE;
    }
}
