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

public class    EndGameScreen extends VBox {
    private final Button endGameButton;
    private final Button restartButton;

    private static final double BUTTON_WIDTH = 200;
    private static final double BUTTON_HEIGHT = 60;

    public EndGameScreen(String message, double screenWidth, double screenHeight) {
        // Full-screen transparent overlay
        this.setPrefSize(screenWidth, screenHeight);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20px;");

        // Text with bigger, bold, and italic font + drop shadow
        Text endGameText = new Text(message);
        endGameText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 70));
        endGameText.setFill(Color.web("#FFFAFA"));
        endGameText.setEffect(new DropShadow(15, Color.BLACK));

        // Styled Restart Button
        restartButton = createStyledButton("Restart");

        // Styled Exit Button
        endGameButton = createStyledButton("Exit");

        // Add components
        this.getChildren().addAll(endGameText, restartButton, endGameButton);
    }

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

        // Button hover effect
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

    public Button getExitButton() {
        return endGameButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
