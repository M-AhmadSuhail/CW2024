package com.example.demo;
import com.example.demo.controller.Main;


public class EnemyProjectile2 extends Projectile {
    private static final String IMAGE_NAME = "Enemyfire1.png";
    private static final int IMAGE_HEIGHT = 15;
    private static final int HORIZONTAL_VELOCITY = -8;


    public EnemyProjectile2(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        super.updatePosition();
    }
    @Override
    public void updateActor() {
        updatePosition();
    }
}