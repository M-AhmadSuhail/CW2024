package com.example.demo.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Projectiles.EnemyProjectile;

/**
 * Represents an enemy plane in the game, which moves horizontally and fires projectiles.
 * Extends the FighterPlane class.
 */
public class EnemyPlane extends FighterPlane {

	// Constants defining the behavior and properties of the enemy plane
	private static final String IMAGE_NAME = "enemyplane.png"; // Image of the enemy plane
	private static final int IMAGE_HEIGHT = 50; // Height of the enemy plane
	private static final int HORIZONTAL_VELOCITY = -6; // Speed at which the enemy plane moves horizontally (leftward)
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0; // X offset for projectile spawn
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0; // Y offset for projectile spawn
	private static final int INITIAL_HEALTH = 1; // Initial health of the enemy plane
	private static final double FIRE_RATE = 0.01; // Probability of firing a projectile each frame

	/**
	 * Constructor for the EnemyPlane object.
	 * Initializes the plane with a specified initial position and health.
	 *
	 * @param initialXPos the initial X position of the enemy plane
	 * @param initialYPos the initial Y position of the enemy plane
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		// Calls the parent class constructor to initialize the enemy plane with the specified image and health
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 * The plane moves to the left at a constant speed.
	 */
	@Override
	public void updatePosition() {
		// Move the plane horizontally at the defined speed
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile from the enemy plane.
	 * The projectile is fired based on the configured fire rate probability.
	 *
	 * @return a new EnemyProjectile if fired, or null if no projectile is fired
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		// Check if the plane should fire a projectile based on the fire rate
		if (Math.random() < FIRE_RATE) {
			// Calculate the spawn position for the projectile
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			// Return a new EnemyProjectile instance at the calculated position
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null; // Return null if no projectile is fired
	}

	/**
	 * Updates the state of the enemy plane, including its position and potential projectile firing.
	 */
	@Override
	public void updateActor() {
		// Update the position of the enemy plane
		updatePosition();
		// Call the parent class' updatePosition method (if any additional behavior exists)
		super.updatePosition();
	}
}
