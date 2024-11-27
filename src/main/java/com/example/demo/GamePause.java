package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCode;
import javafx.animation.Timeline; // Import Timeline

public class GamePause {

    private final Stage pauseStage;
    private final Runnable onResume;
    private final Runnable onExit;
    private final Runnable onSettings;
    private final Timeline gameLoop; // Reference to the game loop

    public GamePause(Stage primaryStage, Runnable onResume, Runnable onExit, Runnable onSettings, Timeline gameLoop) {
        this.onResume = onResume;
        this.onExit = onExit;
        this.onSettings = onSettings;
        this.gameLoop = gameLoop; // Initialize the game loop

        // Create the pause stage
        pauseStage = new Stage();
        pauseStage.initOwner(primaryStage);
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.initStyle(StageStyle.UNDECORATED);
        pauseStage.setResizable(false);

        // Build the pause menu UI
        VBox menu = new VBox(20);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 50;");
        menu.setPrefSize(300, 400);

        Button playButton = new Button("Play");
        playButton.setFont(new Font("Press Start 2P", 20));
        playButton.setTextFill(Color.LIME);
        playButton.setOnAction(event -> resumeGame());

        Button settingsButton = new Button("Settings");
        settingsButton.setFont(new Font("Press Start 2P", 20));
        settingsButton.setTextFill(Color.ORANGE);
        settingsButton.setOnAction(event -> openSettings());

        Button exitButton = new Button("Exit");
        exitButton.setFont(new Font("Press Start 2P", 20));
        exitButton.setTextFill(Color.RED);
        exitButton.setOnAction(event -> exitGame());

        menu.getChildren().addAll(playButton, settingsButton, exitButton);
        menu.setStyle("-fx-alignment: center;");

        Scene pauseScene = new Scene(menu);
        pauseStage.setScene(pauseScene);
    }

    public void show() {
        if (gameLoop != null) {
            gameLoop.pause(); // Pause the game loop
        }
        pauseStage.show();

        // Add key press handler to the scene for 'P'
        pauseStage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                resumeGame();
            }
        });
    }

    public void hide() {
        pauseStage.close();
        if (gameLoop != null) {
            gameLoop.play(); // Resume the game loop
        }
    }

    private void resumeGame() {
        hide();
        if (onResume != null) {
            onResume.run();
        }
    }

    private void exitGame() {
        hide();
        if (onExit != null) {
            onExit.run();
        }
    }

    private void openSettings() {
        hide();
        if (onSettings != null) {
            onSettings.run();
        }
    }
}
