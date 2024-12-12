package com.example.demo.UI;

import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.geometry.Pos;

/**
 * The EndGameScreen class represents a UI component for displaying the end game screen.
 * It includes a message, a restart button, and an exit button, all styled for a polished user experience.
 */
public class EndGameScreen extends VBox {

    // Buttons for user actions: restart the game or exit
    private final Button endGameButton;
    private final Button restartButton;

    // Constants defining button dimensions
    private static final double BUTTON_WIDTH = 200;
    private static final double BUTTON_HEIGHT = 60;

    /**
     * Constructor to create an EndGameScreen with a custom message and screen dimensions.
     *
     * @param message      the message to display, typically indicating the game outcome
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    public EndGameScreen(String message, double screenWidth, double screenHeight) {
        // Set the size, alignment, and background style of the overlay
        this.setPrefSize(screenWidth, screenHeight);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30); // Space between components
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20px;");

        // Create and style the message text
        Text endGameText = new Text(message);
        endGameText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 70));
        endGameText.setFill(Color.web("#FFFAFA"));
        endGameText.setEffect(new DropShadow(15, Color.BLACK));

        // Create styled buttons for Restart and Exit
        restartButton = createStyledButton("Restart");
        endGameButton = createStyledButton("Exit");

        // Add components to the layout
        this.getChildren().addAll(endGameText, restartButton, endGameButton);
    }

    /**
     * Helper method to create a styled button with hover effects.
     *
     * @param text the label of the button
     * @return a styled Button instance
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setStyle("""
            -fx-font-family: 'Arial';
            -fx-font-size: 26px;
            -fx-font-weight: bold;
            -fx-font-style: italic;
            -fx-background-color: #FF6347;
            -fx-text-fill: white;
            -fx-background-radius: 15px;
            -fx-border-radius: 15px;
            -fx-border-color: #FFD700;
            -fx-border-width: 3px;
        """);

        // Define hover effect styles
        button.setOnMouseEntered(e -> button.setStyle("""
            -fx-font-family: 'Arial';
            -fx-font-size: 26px;
            -fx-font-weight: bold;
            -fx-font-style: italic;
            -fx-background-color: #FF4500;
            -fx-text-fill: white;
            -fx-background-radius: 15px;
            -fx-border-radius: 15px;
            -fx-border-color: #FFD700;
            -fx-border-width: 3px;
        """));

        button.setOnMouseExited(e -> button.setStyle("""
            -fx-font-family: 'Arial';
            -fx-font-size: 26px;
            -fx-font-weight: bold;
            -fx-font-style: italic;
            -fx-background-color: #FF6347;
            -fx-text-fill: white;
            -fx-background-radius: 15px;
            -fx-border-radius: 15px;
            -fx-border-color: #FFD700;
            -fx-border-width: 3px;
        """));

        return button;
    }

    /**
     * Retrieves the Exit button.
     *
     * @return the Button instance for exiting the game
     */
    public Button getExitButton() {
        return endGameButton;
    }

    /**
     * Retrieves the Restart button.
     *
     * @return the Button instance for restarting the game
     */
    public Button getRestartButton() {
        return restartButton;
    }
}
