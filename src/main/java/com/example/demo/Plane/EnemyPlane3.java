package com.example.demo.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Projectiles.EnemyProjectile;
import com.example.demo.Projectiles.EnemyProjectile2;
import com.example.demo.Projectiles.EnemyProjectile3;

import java.util.Random;

/**
 * Represents an advanced enemy plane with specific characteristics such as
 * firing projectiles and moving horizontally across the screen.
 * This class extends the FighterPlane class and adds unique behaviors.
 */
public class EnemyPlane3 extends FighterPlane {

    // Constants defining the behavior and properties of the enemy plane
    private static final String IMAGE_NAME = "Enemyheli.png"; // Image for the enemy helicopter
    private static final int IMAGE_HEIGHT = 80; // Height of the enemy plane
    private static final int HORIZONTAL_VELOCITY = -6; // Speed at which the enemy plane moves horizontally (leftward)
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0; // X offset for projectile spawn
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0; // Y offset for projectile spawn
    private static final int INITIAL_HEALTH = 2; // Initial health of the enemy plane
    private static final double BASE_FIRE_RATE = 0.005; // Base probability of firing a projectile
    private static final int MAX_PROJECTILES = 3; // Maximum number of projectiles the enemy can fire
    private static final double FIRE_RATE = 0.01; // Fire rate probability for firing projectiles

    private int currentProjectileCount = 0; // Tracks the current number of projectiles fired
    private double fireCooldown = 1.0; // Initial cooldown period before the next shot
    private static final Random random = new Random(); // Random object for generating random values

    /**
     * Constructor for the EnemyPlane3 object.
     * Initializes the plane with a specified initial position, health, and random fire cooldown.
     *
     * @param initialXPos the initial X position of the enemy plane
     * @param initialYPos the initial Y position of the enemy plane
     */
    public EnemyPlane3(double initialXPos, double initialYPos) {
        // Calls the parent class constructor to initialize the enemy plane with the specified image and health
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        // Set a random initial cooldown between 0.5 and 1.5 seconds
        this.fireCooldown = 0.5 + random.nextDouble();
    }

    /**
     * Fires a projectile from the enemy plane.
     * The projectile is fired based on the configured fire rate probability.
     *
     * @return a new EnemyProjectile3 if fired, or null if no projectile is fired
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        // Check if the plane should fire a projectile based on the fire rate
        if (Math.random() < FIRE_RATE) {
            // Calculate the spawn position for the projectile
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            // Return a new EnemyProjectile3 instance at the calculated position
            return new EnemyProjectile3(projectileXPosition, projectileYPosition);
        }
        return null; // Return null if no projectile is fired
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
     * Updates the state of the enemy plane, including its position and potential projectile firing.
     * Calls the parent class' updatePosition method to ensure all parent behaviors are applied.
     */
    @Override
    public void updateActor() {
        // Update the position of the enemy plane
        updatePosition();
        // Call the parent class' updatePosition method (if any additional behavior exists)
        super.updatePosition();
    }

}
