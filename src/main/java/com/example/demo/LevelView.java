package com.example.demo;
import javafx.scene.control.Label;


import javafx.scene.Group;

public class LevelView {

    private static final double HEART_DISPLAY_X_POSITION = 5;
    private static final double HEART_DISPLAY_Y_POSITION = 25;
    private static final int WIN_IMAGE_X_POSITION = 355;
    private static final int WIN_IMAGE_Y_POSITION = 175;
    private static final int LOSS_SCREEN_X_POSITION = -160;
    private static final int LOSS_SCREEN_Y_POSITION = -375;
    private final Group root;
    private final WinImage winImage;
    private final GameOverImage gameOverImage;
    private final HeartDisplay heartDisplay;

    public LevelView(Group root, int heartsToDisplay) {
        this.root = root;
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
        this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
    }
// In LevelView class
    private Label killCountLabel;

    public void showKillCountLabel() {
        killCountLabel = new Label("Kills: 0");
        killCountLabel.setLayoutX(10);
        killCountLabel.setLayoutY(10);
        root.getChildren().add(killCountLabel); // Assuming 'root' is the game root layout
    }

    public void updateKillCount(int killCount) {
        if (killCountLabel != null) {
            killCountLabel.setText("Kills: " + killCount);
        }
    }

    public void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer());
    }

    public void showWinImage() {
        root.getChildren().add(winImage);
        winImage.showWinImage();
    }

    public void showGameOverImage() {
        root.getChildren().add(gameOverImage);
    }

    public void removeHearts(int heartsRemaining) {
        int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
        for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
            heartDisplay.removeHeart();
        }
    }

}