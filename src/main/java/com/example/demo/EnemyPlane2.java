package com.example.demo;

import java.util.Random;

public class EnemyPlane2 extends FighterPlane {

    private static final String IMAGE_NAME = "Enemyplan2.png";
    private static final int IMAGE_HEIGHT = 50;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 2;
    private static final double BASE_FIRE_RATE = 0.005;  // Base probability of firing
    private static final int MAX_PROJECTILES = 3;

    private int currentProjectileCount = 0;
    private double fireCooldown = 1.0; // Initial cooldown period
    private static final Random random = new Random();

    public EnemyPlane2(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        this.fireCooldown = 0.5 + random.nextDouble(); // Random initial cooldown between 0.5 and 1.5 seconds
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (fireCooldown <= 0 && Math.random() < BASE_FIRE_RATE && currentProjectileCount < MAX_PROJECTILES) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            EnemyProjectile projectile = new EnemyProjectile(projectileXPosition, projectileYPosition);

            // Set the destruction callback
            projectile.setOnDestruction(() -> currentProjectileCount--);

            currentProjectileCount++;
            fireCooldown = 0.5 + random.nextDouble(); // Reset cooldown to a random time between 0.5 and 1.5 seconds
            return projectile;
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
        fireCooldown -= 0.016; // Assuming the game updates at 60 FPS (~1/60 seconds per frame)
    }
}
