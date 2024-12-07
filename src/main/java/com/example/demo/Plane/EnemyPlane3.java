package com.example.demo.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Projectiles.EnemyProjectile;
import com.example.demo.Projectiles.EnemyProjectile2;
import com.example.demo.Projectiles.EnemyProjectile3;

import java.util.Random;

public class EnemyPlane3 extends FighterPlane {

    private static final String IMAGE_NAME = "Enemyheli.png"; // Ensure this image file exists
    private static final int IMAGE_HEIGHT =80;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 2;
    private static final double BASE_FIRE_RATE = 0.005;  // Base probability of firing
    private static final int MAX_PROJECTILES = 3;
    private static final double FIRE_RATE = .01;

    private int currentProjectileCount = 0;
    private double fireCooldown = 1.0; // Initial cooldown period
    private static final Random random = new Random();

    public EnemyPlane3(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        this.fireCooldown = 0.5 + random.nextDouble(); // Random initial cooldown between 0.5 and 1.5 seconds
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectile3(projectileXPosition, projectileYPostion);
        }
        return null;
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }
    @Override
    public void updateActor() {
        updatePosition();
        super.updatePosition();
    }


//    private void removeFromParent() {
//        if (getParent() != null) {
//            getParent().getChildren().remove(this);
//            System.out.println("EnemyPlane2 successfully removed from parent");
//        }
//    }
}
