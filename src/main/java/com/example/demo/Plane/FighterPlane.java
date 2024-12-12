package com.example.demo.Plane;

import com.example.demo.Actor.ActiveActorDestructible;

/**
 * Represents a fighter plane that can take damage, fire projectiles, and track health.
 * This class is abstract and serves as a base class for specific types of fighter planes (e.g., `EnemyPlane`, `UserPlane`).
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	private int health; // The health of the fighter plane

	/**
	 * Constructor for the FighterPlane.
	 * Initializes the fighter plane with an image, dimensions, position, and health.
	 *
	 * @param imageName     the image representing the fighter plane
	 * @param imageHeight   the height of the plane's image
	 * @param initialXPos   the initial X position of the plane
	 * @param initialYPos   the initial Y position of the plane
	 * @param health        the initial health of the plane
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		// Call the parent constructor to initialize image and position
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health; // Set the initial health of the plane
	}

	/**
	 * Abstract method for firing a projectile from the fighter plane.
	 * Each specific fighter plane will implement its own version of this method.
	 *
	 * @return an instance of ActiveActorDestructible representing the fired projectile, or null if no projectile is fired
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the health of the fighter plane by 1 when it takes damage.
	 * If health reaches zero, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--; // Decrease health by 1
		if (healthAtZero()) {
			this.destroy(); // Destroy the plane if health is zero
		}
	}

	/**
	 * Calculates the X position for projectile spawning, factoring in the plane's current position and an offset.
	 *
	 * @param xPositionOffset the offset added to the X position for projectile spawn
	 * @return the calculated X position for the projectile
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset; // Add offsets to current position
	}

	/**
	 * Calculates the Y position for projectile spawning, factoring in the plane's current position and an offset.
	 *
	 * @param yPositionOffset the offset added to the Y position for projectile spawn
	 * @return the calculated Y position for the projectile
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset; // Add offsets to current position
	}

	/**
	 * Checks if the plane's health has reached zero.
	 *
	 * @return true if health is zero, false otherwise
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the fighter plane.
	 *
	 * @return the current health of the plane
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the health of the fighter plane.
	 * This method is protected to allow subclasses to modify the health.
	 *
	 * @param health the new health value to set
	 */
	protected void setHealth(int health) {
		this.health = health;
	}
}
