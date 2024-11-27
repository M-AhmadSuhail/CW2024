package com.example.demo;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

public class EndGameScreen extends VBox {
    private final Button endGameButton;
    private final Button restartButton;

    private static final double BUTTON_WIDTH = 150;
    private static final double BUTTON_HEIGHT = 50;
    private static final double BUTTON_SPACING = 20;

    public EndGameScreen(String message, double screenWidth, double screenHeight) {
        Rectangle background = new Rectangle(screenWidth, screenHeight);
        background.setFill(Color.color(0, 0, 0, 0.7)); // Black with 70% opacity


        Text endGameText = new Text(message);
        endGameText.setFont(Font.font(50));
        endGameText.setFill(Color.WHITE);


    // Initialize UI components
        // Restart Button
        restartButton = new Button("Restart");
        restartButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        restartButton.setLayoutX(screenHeight/ 2 - BUTTON_WIDTH / 2);
        restartButton.setLayoutY(screenWidth / 2 - BUTTON_HEIGHT - BUTTON_SPACING);
        restartButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");

        // Exit Button
        endGameButton = new Button("Exit");
        endGameButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        endGameButton.setLayoutX(screenWidth / 2 - BUTTON_WIDTH / 2);
        endGameButton.setLayoutY(screenHeight / 2 + BUTTON_SPACING);
        endGameButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");

        this.getChildren().addAll(endGameText, restartButton, endGameButton);
        this.setSpacing(20);
        this.setStyle("-fx-alignment: center;");
    }

    public Button getExitButton() {
        return endGameButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}
