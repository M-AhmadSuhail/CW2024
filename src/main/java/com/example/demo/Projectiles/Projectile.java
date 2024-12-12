package com.example.demo.Projectiles;

import com.example.demo.Actor.ActiveActorDestructible;

/**
 * The Projectile class represents a generic projectile in the game.
 * It serves as a base class for all projectiles and provides shared behavior, such as taking damage and being destroyed.
 *
 * This class extends ActiveActorDestructible, inheriting its properties and behavior for actors that can be damaged and destroyed.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructor for creating a Projectile.
	 *
	 * @param imageName    the name of the image file representing the projectile
	 * @param imageHeight  the height of the projectile image
	 * @param initialXPos  the initial horizontal position (X coordinate) of the projectile
	 * @param initialYPos  the initial vertical position (Y coordinate) of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		// Calls the constructor of the superclass (ActiveActorDestructible) to initialize the projectile
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the event of the projectile taking damage.
	 * In the case of a projectile, taking damage results in immediate destruction.
	 */
	@Override
	public void takeDamage() {
		// Destroy the projectile when it takes damage
		this.destroy();
	}
}
