package com.example.demo.LevelController;
import com.example.demo.Boss.BossHealth;
import com.example.demo.UI.*;
import com.example.demo.controller.Main;
import javafx.animation.FadeTransition;
import com.example.demo.controller.Controller;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * Class representing the view for a level in the game.
 */
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

    /**
     * Constructor to initialize the LevelView.
     *
     * @param root            the root group of the scene
     * @param heartsToDisplay the number of hearts to display
     * @param killsToDisplay  the number of kills to display
     */
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

    /**
     * Displays the heart display on the screen.
     */
    public void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer());
    }

    /**
     * Displays the boss health on the screen.
     */
    public void showBossHealth() {
        root.getChildren().add(bossHealth.getContainer());
    }

    /**
     * Displays the kill display on the screen.
     */
    public void showKillDisplay() {
        root.getChildren().add(killDisplay.getContainer());
    }

    /**
     * Updates the boss health display.
     *
     * @param newHealth the new health value of the boss
     */
    public void updateBossHealth(int newHealth) {
        bossHealth.updateHealth(newHealth);
    }

    /**
     * Displays the win image on the screen.
     */
    public void showWinImage() {
        root.getChildren().add(winImage);
        winImage.showWinImage();
    }

    /**
     * Displays the game over image on the screen.
     */
    public void showGameOverImage() {
        root.getChildren().add(gameOverImage);
        gameOverImage.showGameOver();
    }

    /**
     * Removes hearts based on the remaining health.
     *
     * @param heartsRemaining the number of hearts remaining
     */
    public void removeHearts(int heartsRemaining) {
        int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
        for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
            heartDisplay.removeHeart();
        }
    }

    /**
     * Updates the kill counter display.
     *
     * @param newKillCount the new kill count
     */
    public void updateKills(int newKillCount) {
        killDisplay.updateKillCount(newKillCount);
    }

    /**
     * Displays the shield image on the screen.
     */
    public void showShield() {
        if (!root.getChildren().contains(shieldImage)) {
            root.getChildren().add(shieldImage);
        }
        shieldImage.showShield();
    }

    /**
     * Hides the shield image from the screen.
     */
    public void hideShield() {
        shieldImage.hideShield();
    }

    /**
     * Sets the controller for the LevelView.
     *
     * @param controller the controller to be set
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Displays an entry message on the screen.
     * The message appears after a brief pause, and then fades away.
     *
     * @param message the message to display
     */
    public void entryMessage(String message) {
        Label entryMessageLabel = new Label(message);
        entryMessageLabel.setStyle("-fx-font-family: 'Arial'; " +
                "-fx-font-size: 60px; " +
                "-fx-text-fill: #FFFFE0;");   // Removed the background color style

        // Make the label invisible initially
        entryMessageLabel.setOpacity(0.0);

        // Add label to root
        root.getChildren().add(entryMessageLabel);

        // Bind the label's position to center it on the screen
        entryMessageLabel.layoutXProperty().bind(
                Bindings.createDoubleBinding(() -> (Main.getScreenWidth() - entryMessageLabel.getWidth()) / 2,
                        entryMessageLabel.widthProperty()));

        entryMessageLabel.layoutYProperty().bind(
                Bindings.createDoubleBinding(() -> (Main.getScreenHeight() - entryMessageLabel.getHeight()) / 2,
                        entryMessageLabel.heightProperty()));

        // Immediately show the message (no delay)
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), entryMessageLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setOnFinished(e -> {
            // Fade out the message after it has appeared
            PauseTransition waitBeforeFadeOut = new PauseTransition(Duration.seconds(1.5));
            waitBeforeFadeOut.setOnFinished(ev -> {
                FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), entryMessageLabel);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(fadeEvent -> root.getChildren().remove(entryMessageLabel));
                fadeOut.play();
            });
            waitBeforeFadeOut.play();
        });
        fadeIn.play();
    }
}

