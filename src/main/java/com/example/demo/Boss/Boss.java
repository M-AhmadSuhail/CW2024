package com.example.demo.Boss;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.LevelController.LevelView;
import com.example.demo.Plane.FighterPlane;
import com.example.demo.Projectiles.BossProjectile;

import java.util.*;

/**
 * Represents the Boss character in the game, which extends the FighterPlane class.
 * The boss has special abilities like moving in a pattern, firing projectiles, and activating a shield.
 */
public class Boss extends FighterPlane {

	// Constants for the boss's image, position, fire rate, etc.
	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .01;
	private static final int IMAGE_HEIGHT = 100;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 15;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 50;

	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private LevelView levelView;
	private BossHealth bossHealth;

	/**
	 * Constructs a new Boss object with predefined parameters like position and health.
	 * Initializes the move pattern and the health bar for the boss.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();

		// Initialize BossHealth to track the health bar on screen
		bossHealth = new BossHealth(INITIAL_X_POSITION, 10, HEALTH);
	}

	/**
	 * Updates the boss's vertical position based on its current move pattern.
	 * Ensures the boss stays within the defined Y-position bounds.
	 * If the boss exceeds these bounds, the vertical movement is reset.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY(); // Store the current vertical translation
		moveVertically(getNextMove()); // Move the boss vertically based on the next move in the pattern
		double currentPosition = getLayoutY() + getTranslateY(); // Get the current vertical position

		// If the boss is out of bounds, revert its vertical movement
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}

		super.updatePosition(); // Update the position using the parent class's method
	}

	/**
	 * Updates the boss's state by calling the updatePosition and updateShield methods.
	 * This method is called each frame to keep the boss's behavior in sync.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile from the boss if the random chance, determined by the fire rate, permits.
	 * The projectile is fired at the current vertical position of the boss.
	 *
	 * @return a new BossProjectile if the boss fires, null otherwise.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles the boss taking damage. It reduces the boss's health by 1 point unless the boss is shielded.
	 */
	@Override
	public void takeDamage() {
		super.takeDamage(); // Reduce health by 1 when damage is taken
	}

	/**
	 * Reduces the boss's health by the specified amount, ensuring the boss cannot take damage when shielded.
	 *
	 * @param amount the amount of damage to apply.
	 */
	public void reduceHealth(int amount) {
		if (!isShielded) { // Only reduce health if the boss is not shielded
			int newHealth = getHealth() - amount;
			setHealth(newHealth); // Update the boss's health
			bossHealth.updateHealth(newHealth); // Update the health display on screen
			System.out.println("Boss health reduced by " + amount + ". Remaining health: " + newHealth);
		}
	}

	/**
	 * Returns the boss's health status for external access.
	 *
	 * @return the BossHealth instance representing the boss's health bar.
	 */
//	public BossHealth getBossHealth() {
//		return bossHealth;
//	}

	/**
	 * Sets the boss's health, ensuring it does not go below zero.
	 *
	 * @param health the new health value to set.
	 */
	public void setHealth(int health) {
		if (health >= 0) {
			super.setHealth(health); // Use the setter from FighterPlane to update health
		} else {
			super.setHealth(0); // Ensure health does not go negative
		}
	}

	/**
	 * Initializes the move pattern for the boss, alternating between upward and downward movements.
	 * The move pattern is shuffled to make the movement unpredictable.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);  // Add upward movement
			movePattern.add(-VERTICAL_VELOCITY); // Add downward movement
			movePattern.add(ZERO);               // Add no movement
		}
		Collections.shuffle(movePattern); // Randomize the move pattern to make the movement unpredictable
	}

	/**
	 * Sets the LevelView object for the boss, which allows interaction with the game world.
	 *
	 * @param levelView the LevelView instance representing the game level.
	 */
	public void setLevelView(LevelView levelView) {
		this.levelView = levelView;
	}

	/**
	 * Updates the boss's shield status, activating and deactivating it based on the shield's conditions.
	 * The shield's activation and deactivation are also displayed in the game level view.
	 */
	private void updateShield() {
		if (isShielded) { // If the shield is active, track the number of frames it's been active
			framesWithShieldActivated++;
		} else if (shieldShouldBeActivated()) { // If the shield should be activated, do so
			activateShield();
			levelView.showShield(); // Show the shield on the screen
			System.out.println("Shield activated!");
		}

		// If the shield has been active for too long, deactivate it
		if (shieldExhausted()) {
			deactivateShield();
			levelView.hideShield(); // Hide the shield from the screen
			System.out.println("Shield off!");
		}
	}

	/**
	 * Returns the next move for the boss from the move pattern.
	 * The move pattern is shuffled after a certain number of consecutive moves in the same direction.
	 *
	 * @return the next move in the move pattern (either vertical movement or no movement).
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove); // Get the current move from the pattern
		consecutiveMovesInSameDirection++; // Increment the count of consecutive moves in the same direction

		// If the boss has made the same move for too many frames, shuffle the move pattern
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0; // Reset the count
			indexOfCurrentMove++; // Move to the next move in the pattern
		}

		// Reset the pattern index if it reaches the end
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}

		return currentMove; // Return the next move in the pattern
	}

	/**
	 * Determines if the boss should fire a projectile in the current frame based on the fire rate.
	 *
	 * @return true if the boss fires a projectile, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE; // Use the fire rate to decide if the boss fires
	}

	/**
	 * Gets the initial vertical position for the projectile based on the boss's current position.
	 *
	 * @return the initial vertical position for the projectile.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Static method to get the initial X position of the boss.
	 *
	 * @return the initial X position of the boss.
	 */
	static double getBossXPosition() {
		return INITIAL_X_POSITION;
	}

	/**
	 * Determines if the shield should be activated based on a random chance, using the boss's shield probability.
	 *
	 * @return true if the shield should be activated, false otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Checks if the shield has been active for too long and needs to be deactivated.
	 *
	 * @return true if the shield has been active for too long, false otherwise.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the shield, making the boss invulnerable to damage for a short period.
	 */
    protected void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the shield, allowing the boss to take damage again.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0; // Reset the shield timer
	}
	public void move() {
		setLayoutY(getLayoutY() + 10);  // Move the boss down by 10 units
	}
}
