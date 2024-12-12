package com.example.demo.Levels;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.LevelController.LevelView;
import com.example.demo.Plane.EnemyPlane2;
import com.example.demo.LevelController.LevelParent;
import com.example.demo.Plane.UserPlane;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.example.demo.controller.Main.getScreenHeight;

/**
 * LevelTwo represents the second level of the game. The player needs to defeat a set number of enemies
 * while also being introduced to power-ups that can be activated during the game.
 */
public class LevelTwo extends LevelParent {

    // Constants for the background image, next level, and gameplay parameters
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/Level2BG.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelThree"; // Transition to the next level

    private static final int TOTAL_ENEMIES = 7; // Total number of enemies to spawn in this level
    private static final int KILLS_TO_ADVANCE = 15; // Number of kills required to advance to the next level
    private static final double ENEMY_SPAWN_PROBABILITY = 0.25; // Probability of spawning an enemy
    private static final int PLAYER_INITIAL_HEALTH = 4; // Initial health of the player in this level

    /**
     * Constructor to initialize the second level.
     *
     * @param screenHeight the height of the game screen
     * @param screenWidth  the width of the game screen
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        // Initialize the parent class with relevant parameters
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH,
                "LEVEL TWO: Defeat 15 enemies\n" +
                        "Power-Ups Available:\n" +
                        "1 for Shield\n" +
                        "2 for Fire Boost");
        this.currentLevelClassName = this.getClass().getName(); // Set the current level class name
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
        // If the user has reached the kill target, move to the next level
        else if (userHasReachedKillTarget()) {
            goToNextLevel(NEXT_LEVEL);
            System.out.println("Going to next level");
        }
    }

    /**
     * Initialize friendly units in the game, such as the user's plane.
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
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) { // Randomly determine if a new enemy should spawn
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActorDestructible newEnemy = new EnemyPlane2(getScreenWidth(), newEnemyInitialYPosition);

                // Ensure no duplicate enemies are added to the root
                if (!getRoot().getChildren().contains(newEnemy)) {
                    addEnemyUnit(newEnemy);
                    System.out.println("Added new enemy at Y position: " + newEnemyInitialYPosition);
                } else {
                    System.out.println("Duplicate enemy detected, skipping addition.");
                }
            }
        }
    }

    /**
     * Instantiate the level view for Level 2.
     *
     * @return a new LevelView instance
     */
    @Override
    protected LevelView instantiateLevelView() {
        // Set the collision sound for the level
        setCollisionSound("src/main/resources/lvlt.mp3");
        // Return a new LevelView with initial parameters (health and level)
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
     * Initialize the level. This method introduces a delay to show power-up instructions before starting the game.
     */
    @Override
    protected void initializeLevel() {
        // Show the power-up instructions before starting the level
        showPowerUpMessage();

        // Delay the actual start of the level for 5 seconds to show instructions
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(event -> {
            // Now start the level after instructions
            initializeFriendlyUnits();
            spawnEnemyUnits();
        });
        delay.play();
    }

    /**
     * Display the power-up instructions in a new stage.
     */
    private void showPowerUpMessage() {
        Stage powerUpStage = new Stage();
        powerUpStage.initModality(Modality.APPLICATION_MODAL);
        powerUpStage.setTitle("Power-Up Instructions");

        // Display the power-up instructions
        Text powerUpText = new Text("Power-ups are available in this level!\n" +
                "Press '1' for Shield\n" +
                "Press '2' for Fire Boost");
        powerUpText.setFont(Font.font("Press Start 2P", 14));
        powerUpText.setStyle("-fx-fill: yellow;");

        VBox layout = new VBox(20, powerUpText);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: black; -fx-padding: 20;");

        Scene powerUpScene = new Scene(layout, 600, 400);

        // Add ESC key handler to close the window
        powerUpScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                powerUpStage.close();
            }
        });

        powerUpStage.setScene(powerUpScene);
        powerUpStage.showAndWait();
    }
}
