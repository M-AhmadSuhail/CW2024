package com.example.demo.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Boss.Boss;
import com.example.demo.Projectiles.Projectile;
import com.example.demo.Projectiles.UserProjectile;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the user's fighter plane in the game.
 * The player can move the plane, fire projectiles, activate power-ups like shields and one-hit-kill, and take damage.
 */
public class UserPlane extends FighterPlane {

	// Plane attributes and constants
	private static final String IMAGE_NAME = "userplane.png"; // Image of the user's plane
	private static final double Y_UPPER_BOUND = -40; // Upper boundary for vertical movement
	private static final double Y_LOWER_BOUND = 600.0; // Lower boundary for vertical movement
	private static final double INITIAL_X_POSITION = 5.0; // Initial X position of the plane
	private static final double INITIAL_Y_POSITION = 300.0; // Initial Y position of the plane
	private static final int IMAGE_HEIGHT = 100; // Height of the plane's image
	private static final int IMAGE_WIDTH = 100; // Width of the plane's image
	private static final int VERTICAL_VELOCITY = 25; // Vertical speed for plane movement
	private static final int PROJECTILE_X_POSITION = 110; // X position where the projectile spawns
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20; // Y offset for projectile spawn
	private static final long FIRE_RATE_COOLDOWN = 300; // Fire rate cooldown in milliseconds

	// Hitbox dimensions for collision detection
	private static final int HITBOX_HEIGHT = 50;
	private static final int HITBOX_WIDTH = 35;

	// Power-up durations in milliseconds
	private static final int SHIELD_DURATION_MS = 30000; // 30 seconds for shield
	private static final int ONE_HIT_KILL_DURATION_MS = 10000; // 10 seconds for one-hit-kill mode

	private int verticalDirection; // Direction of vertical movement (-1 for up, 1 for down)
	private long lastProjectileTime; // Timestamp of the last projectile fired

	// Player stats
	private int numberOfHits; // Number of hits made by the player

	// Power-up states
	private boolean isShieldActive = false; // Is the shield active
	private boolean isOneHitKillActive = false; // Is one-hit-kill mode active

	/**
	 * Constructor for UserPlane. Initializes the plane with its health.
	 *
	 * @param initialHealth the initial health of the plane
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		verticalDirection = 0; // No vertical movement initially
		lastProjectileTime = 0; // No projectile fired initially
		numberOfHits = 0; // Initialize hit counter
	}

	/**
	 * Updates the plane's position based on vertical movement direction.
	 * Ensures the plane stays within the defined vertical bounds.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY(); // Save the initial Y position
			this.moveVertically(VERTICAL_VELOCITY * verticalDirection); // Move the plane vertically
			double newPosition = getLayoutY() + getTranslateY();
			// Prevent the plane from going out of bounds
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY); // Revert to the previous position if out of bounds
			}
		}
		super.updatePosition();
	}

	/**
	 * Updates the plane's state each frame.
	 * In this case, it updates the position of the plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user's plane if the cooldown period has passed.
	 *
	 * @return a new UserProjectile instance if the plane can fire, or null if it's on cooldown
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastProjectileTime >= FIRE_RATE_COOLDOWN) {
			lastProjectileTime = currentTime; // Update the last projectile time
			return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET)); // Return the new projectile
		}
		return null;
	}

	/**
	 * Handles taking damage. If the shield is active, no damage is taken.
	 * Otherwise, the damage is passed to the superclass.
	 */
	@Override
	public void takeDamage() {
		if (isShieldActive) {
			System.out.println("Shield active! No damage taken.");
		} else {
			super.takeDamage(); // Call the superclass method if shield is not active
		}
	}

	/**
	 * Activates the shield power-up, which makes the plane invincible for a limited time.
	 * The shield will deactivate automatically after the defined duration.
	 */
	public void activateShield() {
		isShieldActive = true;
		System.out.println("Shield activated!");

		// Set a timer to deactivate the shield after the specified duration
		PauseTransition shieldTimer = new PauseTransition(Duration.millis(SHIELD_DURATION_MS));
		shieldTimer.setOnFinished(event -> {
			isShieldActive = false;
			System.out.println("Shield deactivated!");
		});
		shieldTimer.play();
	}

	/**
	 * Displays the shield effect (e.g., visual representation).
	 */
	public void showShield() {
		this.setVisible(true);
		toFront(); // Ensure the shield is displayed in front of the plane
	}

	/**
	 * Checks if the shield is currently active.
	 *
	 * @return true if the shield is active, false otherwise
	 */
	public boolean isShieldActive() {
		return isShieldActive;
	}

	/**
	 * Activates the one-hit-kill power-up, allowing the player to destroy enemies with a single shot.
	 * The effect lasts for a limited time and will be automatically deactivated after the duration.
	 */
	public void activateOneHitKill() {
		isOneHitKillActive = true;
		System.out.println("One-hit-kill mode activated!");

		showOneHitKillEffect();

		PauseTransition oneHitKillTimer = new PauseTransition(Duration.millis(ONE_HIT_KILL_DURATION_MS));
		oneHitKillTimer.setOnFinished(event -> {
			isOneHitKillActive = false;
			hideOneHitKillEffect();
			System.out.println("One-hit-kill mode deactivated!");
		});
		oneHitKillTimer.play();
	}

	/**
	 * Checks if one-hit-kill mode is currently active.
	 *
	 * @return true if one-hit-kill mode is active, false otherwise
	 */
	public boolean isOneHitKillActive() {
		return isOneHitKillActive;
	}

	/**
	 * Displays the visual effect for one-hit-kill mode (e.g., border color change).
	 */
	public void showOneHitKillEffect() {
		System.out.println("One-hit-kill effect shown.");
		this.setStyle("-fx-border-color: gold; -fx-border-width: 5px; -fx-border-radius: 5px;");
	}

	/**
	 * Hides the one-hit-kill effect (restores the style to normal).
	 */
	public void hideOneHitKillEffect() {
		System.out.println("One-hit-kill effect hidden.");
		this.setStyle(""); // Reset style
	}

	/**
	 * Applies the one-hit-kill effect to enemies, destroying the first hit enemy and up to two additional enemies.
	 * If the boss is hit in a boss level, the damage is reduced but the boss is not destroyed.
	 *
	 * @param hitEnemy     the enemy hit by the user's projectile
	 * @param enemyUnits   list of all active enemies
	 * @param root         the root Group containing all actors
	 * @param isBossLevel  true if the current level involves a boss
	 */
	public void applyOneHitKillEffect(ActiveActorDestructible hitEnemy,
									  List<ActiveActorDestructible> enemyUnits,
									  Group root,
									  boolean isBossLevel) {
		// Boss level handling
		if (isBossLevel && hitEnemy instanceof Boss boss) {
			boss.reduceHealth(2); // Reduce boss health by 2 (instead of destroying it)

			if (boss.getHealth() <= 0) {
				boss.destroy();
				root.getChildren().remove(boss);
				enemyUnits.remove(boss);
				incrementHitCountBy(1); // Count as a hit if the boss is destroyed
				System.out.println("Boss destroyed by one-hit-kill power-up!");
			}
			return; // Exit if it's a boss and the boss is handled
		}

		// Destroy the first enemy
		if (!hitEnemy.isDestroyed()) {
			hitEnemy.destroy();
			root.getChildren().remove(hitEnemy);
			enemyUnits.remove(hitEnemy);
			incrementHitCountBy(1); // Count the destroyed enemy
			System.out.println("One-hit-kill destroyed: " + hitEnemy);
		}

		// Destroy up to two additional enemies
		List<ActiveActorDestructible> additionalEnemies = enemyUnits.stream()
				.filter(enemy -> !enemy.isDestroyed()) // Only consider active enemies
				.limit(2) // Destroy at most two additional enemies
				.collect(Collectors.toList());

		for (ActiveActorDestructible enemy : additionalEnemies) {
			enemy.destroy();
			root.getChildren().remove(enemy);
			enemyUnits.remove(enemy);
			incrementHitCountBy(1); // Count each destroyed enemy
			System.out.println("One-hit-kill destroyed additional enemy: " + enemy);
		}
	}

	/**
	 * Increments the player's hit count by 1.
	 */
	public void incrementHitCount() {
		numberOfHits++;
	}

	/**
	 * Increments the player's hit count by a specific value.
	 *
	 * @param count the number of hits to add
	 */
	public void incrementHitCountBy(int count) {
		numberOfHits += count;
	}

	/**
	 * Gets the total number of hits made by the player.
	 *
	 * @return the number of hits
	 */
	public int getNumberOfHits() {
		return numberOfHits;
	}

	/**
	 * Checks if the plane is currently moving vertically (up or down).
	 *
	 * @return true if the plane is moving, false otherwise
	 */
	private boolean isMoving() {
		return verticalDirection != 0;
	}

	/**
	 * Moves the plane upwards by setting the vertical direction to -1.
	 */
	public void moveUp() {
		verticalDirection = -1;
	}

	/**
	 * Moves the plane downwards by setting the vertical direction to 1.
	 */
	public void moveDown() {
		verticalDirection = 1;
	}

	/**
	 * Stops the plane from moving vertically by setting the vertical direction to 0.
	 */
	public void stop() {
		verticalDirection = 0;
	}

	/**
	 * Checks if the plane is hit by a given projectile using collision detection.
	 *
	 * @param projectile the projectile to check collision with
	 * @return true if the plane is hit by the projectile, false otherwise
	 */
	public boolean isHitBy(Projectile projectile) {
		double planeX = getLayoutX();
		double planeY = getLayoutY();
		return projectile.getX() >= planeX && projectile.getX() <= planeX + HITBOX_WIDTH
				&& projectile.getY() >= planeY && projectile.getY() <= planeY + HITBOX_HEIGHT;
	}
}
