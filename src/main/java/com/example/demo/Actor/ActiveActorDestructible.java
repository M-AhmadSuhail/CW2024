package com.example.demo.Actor;
import com.example.demo.controller.Main;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	// Variable to track if the actor is destroyed
	private boolean isDestroyed;

	// Constructor initializing the actor's image, height, and initial position
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false; // Initially, the actor is not destroyed
	}

	/**
	 * Checks if the actor is out of bounds based on the X-coordinate.
	 * @param x The X-coordinate of the actor.
	 * @param screenWidth The width of the screen.
	 * @return true if the actor is out of bounds, false otherwise.
	 */
	public boolean isOutOfBounds(double x, double screenWidth) {
		return x < 0 || x > screenWidth; // Check if X is out of the screen's width
	}

	/**
	 * Updates the actor's position and checks if it's out of bounds.
	 * If the actor is out of bounds, it will be destroyed.
	 */
	@Override
	public void updatePosition() {
		double x = getLayoutX() + getTranslateX(); // Get the actor's current X position

		// Check if the actor is out of bounds and destroy if true
		if (isOutOfBounds(x, Main.getScreenWidth())) {
			System.out.println(this.getClass().getSimpleName() + " is out of bounds.");
			this.destroy(); // Call destroy if the actor is out of bounds
		}
	}

	// Abstract method that will be implemented in subclasses to update the actor's behavior
	public abstract void updateActor();

	// Abstract method to handle the actor taking damage, implemented by subclasses
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed by setting the isDestroyed flag to true.
	 */
	@Override
	public void destroy() {
		setDestroyed(true); // Mark the actor as destroyed
	}

	/**
	 * Sets the destroyed status of the actor.
	 * @param isDestroyed true if the actor is destroyed, false otherwise.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed; // Update the destruction status
	}

	/**
	 * Returns whether the actor is destroyed or not.
	 * @return true if the actor is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed; // Return the destruction status
	}
}
