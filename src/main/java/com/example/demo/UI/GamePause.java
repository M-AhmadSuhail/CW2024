package com.example.demo.UI;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.effect.DropShadow;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

/**
 * The GamePause class represents the pause menu in the game,
 * providing options to resume, restart, or exit the game.
 */
public class GamePause {

    // Constants for screen dimensions
    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;

    // Runnable actions for pause menu buttons
    private final Runnable onResume;
    private final Runnable onRestart;
    private final Runnable onExit;

    // Stage for the pause menu
    protected Stage pauseStage;

    /**
     * Constructor to initialize the GamePause menu with action handlers.
     *
     * @param onResume the action to perform when resuming the game
     * @param onRestart the action to perform when restarting the level
     * @param onExit the action to perform when exiting the game
     */
    public GamePause(Runnable onResume, Runnable onRestart, Runnable onExit) {
        this.onResume = onResume;
        this.onRestart = onRestart;
        this.onExit = onExit;
    }

    /**
     * Displays the pause menu.
     */
    public void show() {
        showPausePopup();
    }

    /**
     * Creates and displays the pause menu popup with buttons for Resume, Restart, and Exit.
     */
    public void showPausePopup() {
        Platform.runLater(() -> {
            // Create a new Stage for the pause menu
            pauseStage = new Stage();
            pauseStage.initModality(Modality.APPLICATION_MODAL);
            pauseStage.initStyle(StageStyle.TRANSPARENT);  // Transparent stage background
            pauseStage.setResizable(false);

            // Create buttons
            Button resumeButton = createStyledButton("Resume Game", Color.GREEN);
            resumeButton.setOnAction(event -> resumeGame());

            Button restartButton = createStyledButton("Restart Level", Color.ORANGE);
            restartButton.setOnAction(event -> restartLevel());

            Button exitButton = createStyledButton("Exit Game", Color.RED);
            exitButton.setOnAction(event -> exitGame());

            // VBox layout for buttons
            VBox layout = new VBox(20, resumeButton, restartButton, exitButton);
            layout.setAlignment(Pos.CENTER);
            layout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 30; -fx-border-radius: 15;");

            // Spacer for positioning
            Region spacer = new Region();
            VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            layout.getChildren().add(spacer);

            // Positioning layout on screen
            layout.setTranslateX((SCREEN_WIDTH - layout.getWidth()) / 2 - 650);
            layout.setTranslateY((SCREEN_HEIGHT - layout.getHeight()) / 2 - 225);

            // Create scene with transparent background
            Scene scene = new Scene(layout);
            scene.setFill(Color.TRANSPARENT);
            pauseStage.setScene(scene);

            // Display the pause menu
            pauseStage.show();
        });
    }

    /**
     * Creates a styled button with a hover effect and drop shadow.
     *
     * @param text the button text
     * @param textColor the color of the text
     * @return a styled Button instance
     */
    private Button createStyledButton(String text, Color textColor) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", 20));
        button.setTextFill(textColor);
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-border-radius: 10; -fx-padding: 10;");

        // Hover effect
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-border-radius: 10; -fx-padding: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-border-radius: 10; -fx-padding: 10;"));

        // Drop shadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(2.0);
        dropShadow.setOffsetY(2.0);
        dropShadow.setColor(Color.GRAY);
        button.setEffect(dropShadow);

        return button;
    }

    /**
     * Handles the action for resuming the game and closes the pause menu.
     */
    private void resumeGame() {
        if (onResume != null) {
            onResume.run();
        }
        closePauseMenu();
    }

    /**
     * Handles the action for restarting the level and closes the pause menu.
     */
    private void restartLevel() {
        if (onRestart != null) {
            onRestart.run();
        }
        closePauseMenu();
    }

    /**
     * Handles the action for exiting the game and closes the pause menu.
     */
    private void exitGame() {
        if (onExit != null) {
            onExit.run();
        }
        closePauseMenu();
    }

    /**
     * Closes the pause menu popup.
     */
    private void closePauseMenu() {
        if (pauseStage != null) {
            pauseStage.close();
        }
    }
}
