package com.example.demo.Levels;

import com.example.demo.Boss.Boss;
import com.example.demo.Boss.BossHealth;
import com.example.demo.LevelController.LevelParent;
import com.example.demo.LevelController.LevelView;
import com.example.demo.Plane.UserPlane;
import javafx.scene.Scene;

/**
 * LevelBoss represents the final level of the game where the player faces a boss.
 * It extends LevelParent and handles the specific logic for the boss battle.
 */
public class LevelBoss extends LevelParent {

	// Constants for the background image and player's initial health
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/LevelBoss.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;

	// Boss and boss health objects
	private final Boss boss;
	private final BossHealth bossHealth;

	/**
	 * Constructor to initialize the boss level.
	 *
	 * @param screenHeight the height of the game screen
	 * @param screenWidth  the width of the game screen
	 */
	public LevelBoss(double screenHeight, double screenWidth) {
		// Initialize the parent class with relevant parameters
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, "Final LEVEL: Defeat THE BOSS");

		// Initialize the boss and set up the level view
		this.boss = new Boss();
		this.boss.setLevelView(levelView);

		// Initialize the boss health bar
		this.bossHealth = new BossHealth(850, 25, boss.getHealth());

		// Set the class name for the next level
		this.currentLevelClassName = "com.example.demo.Levels.LevelThree";
	}

	/**
	 * Check if the game is over by verifying if the player or boss is destroyed.
	 */
	@Override
	protected void checkIfGameOver() {
		// Update the boss's health bar
		bossHealth.updateHealth(boss.getHealth());

		// Check if the player is destroyed, triggering a loss
		if (userIsDestroyed()) {
			loseGame();  // Call loseGame to end the game
		}

		// Check if the boss is destroyed, triggering a win
		if (boss.isDestroyed()) {
			System.out.println("Boss Destroyed");
			winGame();  // Call winGame to end the game
		}
	}
	/**
	 * Initialize friendly units in the game, such as the player's plane.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		// Add the user plane to the root node
		getRoot().getChildren().add(getUser());

		// Bind the feature keys (such as power-ups) to the user plane
		Scene scene = getRoot().getScene(); // Get the scene associated with the root
		if (scene != null) {
			bindFeatureKeys(scene, (UserPlane) getUser());
		} else {
			System.err.println("Scene not initialized. Ensure the root node is added to a scene.");
		}
	}

	/**
	 * Spawn enemy units for the level. In this case, spawn the boss.
	 */
	@Override
	protected void spawnEnemyUnits() {
		// Spawn the boss if there are no existing enemies and the boss is not already destroyed
		if (getCurrentNumberOfEnemies() == 0 && !boss.isDestroyed()) {
			System.out.println("SPAWNING BOSS");
			addEnemyUnit(boss);
			getRoot().getChildren().add(bossHealth.getContainer());
		}
	}

	/**
	 * Update the hit count based on collisions and enemies eliminated.
	 *
	 * @param collisionDetected if a collision is detected
	 * @param enemiesEliminated number of enemies eliminated
	 */
	@Override
	protected void updateHitCount(boolean collisionDetected, int enemiesEliminated) {
		// Update the user's hit count when a collision is detected
		if (collisionDetected) {
			UserPlane user = getUser();
			user.incrementHitCountBy(1); // Update hit count for multiple kills
			System.out.println("User hit-count updated: " + user.getNumberOfHits());
		}
	}

	/**
	 * Instantiate the level view for the boss level.
	 *
	 * @return a new LevelView instance
	 */
	@Override
	protected LevelView instantiateLevelView() {
		// Set the collision sound for the level
		setCollisionSound("src/main/resources/lvlb.mp3");
		// Create a new LevelView with initial parameters
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, 0);
	}

	/**
	 * Initialize the level. This method is used for any setup that needs to be done
	 * when the level starts. Currently empty for LevelBoss.
	 */
	@Override
	public void initializeLevel() {
		// Call the parent method with the current level name
		super.initializeLevel("Boss", 15, "Defeat The Boss!!");
	}
}
