package com.example.demo.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Projectiles.Projectile;
import com.example.demo.Projectiles.UserProjectile;

/**
 * Represents the user's fighter plane in Level Two of the game, which has
 * different characteristics such as slower movement and a longer cooldown
 * between shots compared to the regular UserPlane.
 */
public class UserPlane2 extends FighterPlane {

    // Constants for the plane's image and movement properties
    private static final String IMAGE_NAME = "UserPlane2.png"; // Image for the user's plane in Level Two
    private static final double Y_UPPER_BOUND = -40; // Upper boundary for the vertical movement
    private static final double Y_LOWER_BOUND = 600.0; // Lower boundary for the vertical movement
    private static final double INITIAL_X_POSITION = 5.0; // Initial X position of the plane
    private static final double INITIAL_Y_POSITION = 300.0; // Initial Y position of the plane
    private static final int IMAGE_HEIGHT = 100; // Height of the plane's image
    private static final int IMAGE_WIDTH = 100; // Width of the plane's image
    private static final int VERTICAL_VELOCITY = 6; // Slower vertical movement for increased difficulty
    private static final int PROJECTILE_X_POSITION = 110; // X position where the projectile spawns
    private static final int PROJECTILE_Y_POSITION_OFFSET = 20; // Y offset for the projectile's spawn point
    private static final long FIRE_RATE_COOLDOWN = 500; // Cooldown between shots (500 ms = 0.5 second)

    // Instance variables for the plane's state
    private int verticalDirection; // Direction of vertical movement (-1 for up, 1 for down)
    private int numberOfHits; // Number of hits made by the plane (not used here but can be expanded)
    private long lastProjectileTime; // Time of the last fired projectile

    /**
     * Constructor for UserPlane2, initializing with the plane's health.
     *
     * @param initialHealth the initial health of the plane
     */
    public UserPlane2(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
        verticalDirection = 0; // Initially no vertical movement
        lastProjectileTime = 0; // No projectile fired initially
    }

    /**
     * Updates the plane's position by moving it vertically based on the current direction.
     * The plane is constrained within the vertical boundaries.
     */
    @Override
    public void updatePosition() {
        // Only update if the plane is moving vertically
        if (isMoving()) {
            double initialTranslateY = getTranslateY(); // Save initial Y position
            this.moveVertically(VERTICAL_VELOCITY * verticalDirection); // Move the plane vertically
            double newPosition = getLayoutY() + getTranslateY();
            // Ensure the plane doesn't move out of bounds
            if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
                this.setTranslateY(initialTranslateY); // Revert to the previous position if out of bounds
            }
        }
        super.updatePosition(); // Call the superclass's updatePosition method
    }

    /**
     * Updates the actor each frame. In this case, it just updates the position.
     */
    @Override
    public void updateActor() {
        updatePosition(); // Update the position based on the vertical movement
    }

    /**
     * Fires a projectile from the user's plane if the cooldown period has passed.
     * The cooldown is set to 0.5 seconds.
     *
     * @return a new UserProjectile if the cooldown has passed, or null if it's on cooldown
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        long currentTime = System.currentTimeMillis(); // Get the current system time
        // Check if enough time has passed since the last shot
        if (currentTime - lastProjectileTime >= FIRE_RATE_COOLDOWN) {
            lastProjectileTime = currentTime; // Update the last shot time
            return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET)); // Return a new projectile
        } else {
            System.out.println("Cooldown active, cannot fire yet!"); // Inform the player that the shot is on cooldown
            return null; // Return null if the cooldown is still active
        }
    }

    /**
     * Checks if the plane is currently moving vertically.
     *
     * @return true if the plane is moving, false otherwise
     */
    private boolean isMoving() {
        return verticalDirection != 0; // The plane is moving if the vertical direction is not zero
    }

}
