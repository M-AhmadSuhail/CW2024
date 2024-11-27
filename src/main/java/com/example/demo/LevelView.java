package com.example.demo;

import com.example.demo.controller.Controller;
import javafx.animation.Timeline;
import javafx.scene.Group;

public class LevelView {

    private static final double HEART_DISPLAY_X_POSITION = 5;
    private static final double HEART_DISPLAY_Y_POSITION = 25;
    private static final double KILL_DISPLAY_X_POSITION = 1000;
    private static final double KILL_DISPLAY_Y_POSITION = 25;
    private static final double SHIELD_X_POSITION = 1150;
    private static final double SHIELD_Y_POSITION = 500;
    private static final double BOSS_HEALTH_X_POSITION = 1150;
    private static final double BOSS_HEALTH_Y_POSITION = 75;
    private static final int WIN_IMAGE_X_POSITION = 355;
    private static final int WIN_IMAGE_Y_POSITION = 175;
    private static final int LOSS_SCREEN_X_POSITION = 355;
    private static final int LOSS_SCREEN_Y_POSITION = 175;

    private final Group root;
    private final WinImage winImage;
    private final GameOverImage gameOverImage;
    private final HeartDisplay heartDisplay;
    private final KillDisplay killDisplay;
    private final ShieldImage shieldImage;
    private BossHealth bossHealth;

    private Timeline timeline;
    private Controller controller;

    public LevelView(Group root, int heartsToDisplay, int killsToDisplay) {
        this.root = root;
        this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.killDisplay = new KillDisplay(KILL_DISPLAY_X_POSITION, KILL_DISPLAY_Y_POSITION);
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
        this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
        this.bossHealth = new BossHealth(BOSS_HEALTH_X_POSITION, BOSS_HEALTH_Y_POSITION, 5);
        this.timeline = new Timeline();
    }

    public void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer());
    }

    public void showBossHealth() {
        root.getChildren().add(bossHealth.getContainer());
    }

    public void showKillDisplay() {
        root.getChildren().add(killDisplay.getContainer());
    }

    public void updateBossHealth(int newHealth) {
        bossHealth.updateHealth(newHealth);
    }

    public void showWinImage() {
        root.getChildren().add(winImage);
        winImage.showWinImage();
    }

    public void showGameOverImage() {
        root.getChildren().add(gameOverImage);
        gameOverImage.showGameOver();
    }

    public void removeHearts(int heartsRemaining) {
        int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
        for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
            heartDisplay.removeHeart();
        }
    }

    public void updateKills(int newKillCount) {
        killDisplay.updateKillCount(newKillCount);
    }

    public void showShield() {
        if (!root.getChildren().contains(shieldImage)) {
            root.getChildren().add(shieldImage);
        }
        shieldImage.showShield();
    }

    public void hideShield() {
        shieldImage.hideShield();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
