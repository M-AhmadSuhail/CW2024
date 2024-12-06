package com.example.demo.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Projectiles.Projectile;
import com.example.demo.Projectiles.UserProjectile;

public class UserPlane2 extends FighterPlane {

    private static final String IMAGE_NAME = "UserPlane2.png"; // Different image for Level Two
    private static final double Y_UPPER_BOUND = -40;
    private static final double Y_LOWER_BOUND = 600.0;
    private static final double INITIAL_X_POSITION = 5.0;
    private static final double INITIAL_Y_POSITION = 300.0;
    private static final int IMAGE_HEIGHT = 100;
    private static final int IMAGE_WIDTH = 100;
    private static final int VERTICAL_VELOCITY = 6;  // Slower movement for increased difficulty
    private static final int PROJECTILE_X_POSITION = 110;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
    private static final long FIRE_RATE_COOLDOWN = 500;  // Introduce a 0.5-second cooldown between shots

    // Custom hitbox dimensions for Level Two
    private static final int HITBOX_HEIGHT = 45;  // Smaller hitbox for more precision
    private static final int HITBOX_WIDTH = 30;

    private int verticalDirection;
    private int numberOfHits;
    private long lastProjectileTime;

    public UserPlane2(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
        verticalDirection = 0;
        lastProjectileTime = 0;
    }

    @Override
    public void updatePosition() {
        if (isMoving()) {
            double initialTranslateY = getTranslateY();
            this.moveVertically(VERTICAL_VELOCITY * verticalDirection);
            double newPosition = getLayoutY() + getTranslateY();
            if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
                this.setTranslateY(initialTranslateY);
            }
        }
        super.updatePosition();
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastProjectileTime >= FIRE_RATE_COOLDOWN) {
            lastProjectileTime = currentTime;
            return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
        } else {
            System.out.println("Cooldown active, cannot fire yet!");
            return null;
        }
    }

    private boolean isMoving() {
        return verticalDirection != 0;
    }

    public void moveUp() {
        verticalDirection = -1;
    }

    public void moveDown() {
        verticalDirection = 1;
    }

    public void stop() {
        verticalDirection = 0;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void incrementHitCount() {
        numberOfHits++;
    }

    // Custom hitbox method for Level Two
    public boolean isHitBy(Projectile projectile) {
        double planeX = getLayoutX();
        double planeY = getLayoutY();
        return projectile.getX() >= planeX && projectile.getX() <= planeX + HITBOX_WIDTH
                && projectile.getY() >= planeY && projectile.getY() <= planeY + HITBOX_HEIGHT;
    }
}
