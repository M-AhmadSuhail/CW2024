package com.example.demo.Projectiles;

/**
 * The UserProjectile class represents a projectile fired by the player in the game.
 * It extends the Projectile class and defines its unique properties such as image, size, and velocity.
 */
public class UserProjectile extends Projectile {

	// Constants defining the properties of the projectile
	private static final String IMAGE_NAME = "userfire.png";  // The image file for the projectile
	private static final int IMAGE_HEIGHT = 10;  // The height of the projectile image
	private static final int HORIZONTAL_VELOCITY = 15;  // Horizontal speed of the projectile (positive for right movement)

	/**
	 * Constructor to create a UserProjectile with specified initial X and Y positions.
	 *
	 * @param initialXPos the initial horizontal position (X coordinate) of the projectile
	 * @param initialYPos the initial vertical position (Y coordinate) of the projectile
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		// Calls the superclass (Projectile) constructor with the given positions and predefined values
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile based on its horizontal velocity.
	 * This method moves the projectile horizontally to the right and updates its position in the game world.
	 */
	@Override
	public void updatePosition() {
		// Moves the projectile horizontally by applying the velocity
		moveHorizontally(HORIZONTAL_VELOCITY);
		// Calls the superclass method to handle other updates
		super.updatePosition();
	}

	/**
	 * Updates the state of the UserProjectile.
	 * This method is responsible for updating the position of the projectile.
	 */
	@Override
	public void updateActor() {
		// Updates the position of the projectile by calling updatePosition
		updatePosition();
	}
}
