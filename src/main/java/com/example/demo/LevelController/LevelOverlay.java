package com.example.demo.LevelController;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LevelOverlay {
    private static final String CUSTOM_FONT = "/com/example/demo/Fonts/PixelifySans-VariableFont_wght.ttf"; // Path to custom font
    private final Group root;
    private final Runnable startGame;
    private final Font customFont;

    public LevelOverlay(Group root, Runnable startGame) {
        this.root = root;
        this.startGame = startGame;

        // Load the custom font
        customFont = Font.loadFont(getClass().getResourceAsStream(CUSTOM_FONT), 36);
        if (customFont == null) {
            System.err.println("Error: Unable to load custom font at path: " + CUSTOM_FONT);
        }
    }

    // Show overlay with dynamic level name, kills needed, and an optional message
    public void showLevelOverlay(String levelName, int killsNeeded, String message) {
        // Create the overlay container
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: black;"); // Black background
        overlay.setPrefSize(1300, 750); // Match screen proportions

        // Format the main overlay text
        String overlayTextString = String.format("Level: %s\nKills Needed: %d", levelName, killsNeeded);

        // Main overlay label for the level info
        Label overlayText = new Label(overlayTextString);

        // Apply the custom font and styling
        if (customFont != null) {
            overlayText.setFont(customFont);
        }
        overlayText.setStyle(
                "-fx-text-fill: #ffff00; " + // Yellow color
                        "-fx-effect: dropshadow(gaussian, #ffff00, 10, 0.3, 0, 0);" // Reduced drop shadow
        );
        overlayText.setWrapText(true);  // Enable wrapping
        overlayText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER); // Center text horizontally

        // Format and create the label for the optional message
        String formattedMessage = message != null && !message.isEmpty() ? message : "";
        Label messageLabel = new Label(formattedMessage);

        if (customFont != null) {
            messageLabel.setFont(customFont);
        }
        messageLabel.setStyle(
                "-fx-text-fill: #0000ff; " + // Blue color for the message
                        "-fx-effect: dropshadow(gaussian, #0000ff, 10, 0.3, 0, 0);" // Reduced drop shadow
        );
        messageLabel.setWrapText(true);  // Enable wrapping
        messageLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER); // Center text horizontally

        // Add the overlay text and message to a VBox for vertical alignment
        VBox overlayContent = new VBox(20, overlayText, messageLabel); // Spacing of 20
        overlayContent.setAlignment(Pos.CENTER);  // Center VBox content

        // Add the content to the overlay
        overlay.getChildren().add(overlayContent);

        // Add the overlay to the root
        root.getChildren().add(overlay);

        // Fade-out transition for the overlay
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), overlay);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Remove overlay and start the game after fade-out
        fadeOut.setOnFinished(e -> {
            root.getChildren().remove(overlay);
            startGame.run(); // Start the game
        });

        // Pause before fade-out
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> fadeOut.play());

        delay.play(); // Begin the delay
    }

}