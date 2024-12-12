package com.example.demo.Projectiles;

/**
 * The EnemyProjectile class represents a projectile fired by an enemy in the game.
 * It extends the Projectile class and includes additional behavior for handling destruction.
 */
public class EnemyProjectile extends Projectile {

	// Callback to be executed when the projectile is destroyed
	private Runnable onDestruction;

	// Constants defining the properties of the projectile
	private static final String IMAGE_NAME = "enemyFire.png";  // The image file for the projectile
	private static final int IMAGE_HEIGHT = 15;  // The height of the projectile image
	private static final int HORIZONTAL_VELOCITY = -10;  // Horizontal speed of the projectile (negative for left movement)

	/**
	 * Constructor to create an EnemyProjectile with specified initial X and Y positions.
	 *
	 * @param initialXPos the initial horizontal position (X coordinate) of the projectile
	 * @param initialYPos the initial vertical position (Y coordinate) of the projectile
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		// Calls the superclass (Projectile) constructor with the given positions and predefined values
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile based on its horizontal velocity.
	 * This method moves the projectile and updates its position in the game world.
	 */
	@Override
	public void updatePosition() {
		// Moves the projectile horizontally by applying the velocity
		moveHorizontally(HORIZONTAL_VELOCITY);
		// Calls the superclass method to handle other updates
		super.updatePosition();
	}

	/**
	 * Sets a callback to be executed when the projectile is destroyed.
	 *
	 * @param onDestruction a Runnable representing the action to perform upon destruction
	 */
	public void setOnDestruction(Runnable onDestruction) {
		this.onDestruction = onDestruction;
	}

	/**
	 * Destroys the projectile and triggers the destruction callback if it exists.
	 * This method overrides the superclass method to add custom destruction behavior.
	 */
	@Override
	public void destroy() {
		// Executes the onDestruction callback if it is defined
		if (onDestruction != null) {
			onDestruction.run();
		}
		// Calls the superclass method to handle additional destruction logic
		super.destroy();
	}

	/**
	 * Updates the state of the EnemyProjectile.
	 * This method is responsible for updating the position of the projectile.
	 */
	@Override
	public void updateActor() {
		// Updates the position of the projectile by calling updatePosition
		updatePosition();
	}
}
