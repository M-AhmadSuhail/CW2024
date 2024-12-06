package com.example.demo.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Projectiles.EnemyProjectile2;

public class EnemyPlane3 extends FighterPlane {

    private static final String IMAGE_NAME = "EnemyPlane3.png"; // Unique image for Level Three
    private static final int IMAGE_HEIGHT = 60; // Slightly larger for visual differentiation
    private static final int HORIZONTAL_VELOCITY = -10; // Even faster for increased challenge
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 3; // Increased health to make them harder to destroy
    private static final double FIRE_RATE = 0.05; // Aggressive fire rate

    public EnemyPlane3(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectile2(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }
}
