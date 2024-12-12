package com.example.demo.Projectiles;

/**
 * The BossProjectile class represents a projectile fired by a boss in the game.
 * It extends the Projectile class and has predefined properties such as image, speed, and initial position.
 */
public class BossProjectile extends Projectile {

	// Constants defining the properties of the projectile
	private static final String IMAGE_NAME = "fireball.png";  // The image file for the projectile
	private static final int IMAGE_HEIGHT = 25;  // The height of the projectile image
	private static final int HORIZONTAL_VELOCITY = -15;  // Horizontal speed of the projectile (negative for left movement)
	private static final int INITIAL_X_POSITION = 950;  // The initial x position of the projectile (on the screen)

	/**
	 * Constructor to create a BossProjectile with a specified initial Y position.
	 * The initial X position and other properties are predefined.
	 *
	 * @param initialYPos the initial vertical position (Y coordinate) of the projectile
	 */
	public BossProjectile(double initialYPos) {
		// Calls the superclass (Projectile) constructor with the predefined values
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the projectile based on its horizontal velocity.
	 * This method is responsible for moving the projectile horizontally and then updating its position.
	 */
	@Override
	public void updatePosition() {
		// Moves the projectile horizontally by applying the velocity
		moveHorizontally(HORIZONTAL_VELOCITY);
		// Calls the superclass method to update the vertical position and handle other logic
		super.updatePosition();
	}

	/**
	 * Updates the state of the BossProjectile.
	 * This method is called to update the position of the projectile as well as any other actor-related behavior.
	 */
	@Override
	public void updateActor() {
		// Updates the position of the projectile by calling updatePosition
		updatePosition();
	}
}
