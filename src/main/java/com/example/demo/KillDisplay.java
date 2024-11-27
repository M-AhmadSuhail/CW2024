package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class KillDisplay {

    private static final String KILL_IMAGE_NAME = "/com/example/demo/images/kill.png"; // Path to kill image
    private static final int IMAGE_HEIGHT = 50; // Height for each image
    private HBox container;
    private final double containerXPosition;
    private final double containerYPosition;
    private Label killCountLabel; // Label to display the number of kills

    public KillDisplay(double xPosition, double yPosition) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        initializeContainer();
        initializeKillImage();
        initializeKillCountLabel();
    }

    // Initialize the container (HBox)
    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);
        container.setSpacing(10); // Add spacing between image and label
    }

    // Initialize the kill image
    private void initializeKillImage() {
        ImageView killImage = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource(KILL_IMAGE_NAME)).toExternalForm()));
        killImage.setFitHeight(IMAGE_HEIGHT);
        killImage.setPreserveRatio(true);
        container.getChildren().add(killImage);
    }

    // Initialize the label to display the number of kills
    private void initializeKillCountLabel() {
        killCountLabel = new Label("0"); // Default to 0 kills
        killCountLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"); // Style the label
        container.getChildren().add(killCountLabel);
    }

    // Update the number of kills
    public void updateKillCount(int kills) {
        killCountLabel.setText(String.valueOf(kills));
    }
    public void resetKillCount() {
        updateKillCount(0);
    }

    // Method to get the container (HBox) to add to the scene
    public HBox getContainer() {
        return container;
    }
}
