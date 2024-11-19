/*
package com.example.demo;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LevelDisplay {

    /**
     * Displays the level name overlay on the provided StackPane.
     *
     * @param rootPane  The root StackPane where the overlay will be displayed.
     * @param levelName The name of the level to display.
     * @param onFinished Callback to run after the display duration ends.
     */
/*
    public static void displayLevelName(StackPane rootPane, String levelName, Runnable onFinished) {
        // Create the overlay label
        Label levelLabel = new Label(levelName);
        levelLabel.setTextFill(Color.LIME);
        levelLabel.setFont(new Font("Press Start 2P", 50)); // Retro font
        levelLabel.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.8); " +
                        "-fx-border-color: limegreen; " +
                        "-fx-border-width: 5px; " +
                        "-fx-padding: 20px;"
        );

        // Add the label to the root pane
        rootPane.getChildren().add(levelLabel);

        // Remove the label after 2 seconds
        PauseTransition fadeOut = new PauseTransition(Duration.seconds(2));
        fadeOut.setOnFinished(event -> {
            rootPane.getChildren().remove(levelLabel);
            if (onFinished != null) {
                onFinished.run();
            }
        });
        fadeOut.play();
    }
}
*/