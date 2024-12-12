package com.example.demo.UI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * The KillDisplay class is responsible for displaying an image and a label that shows
 * the number of kills a player has achieved in the game. The image is shown alongside
 * the kill count, and it updates dynamically as the kill count changes.
 */
public class KillDisplay {

    private static final String KILL_IMAGE_NAME = "/com/example/demo/images/kill1.png"; // Path to the image for kills
    private static final int IMAGE_HEIGHT = 50; // Height of the image representing kills
    private HBox container;  // The HBox container to hold the image and kill count label
    private final double containerXPosition;  // X-coordinate of the container's position on the screen
    private final double containerYPosition;  // Y-coordinate of the container's position on the screen
    private Label killCountLabel;  // Label to display the number of kills

    /**
     * Constructor to initialize the KillDisplay with its position on the screen.
     * It also initializes the container, image, and label.
     *
     * @param xPosition The X-coordinate of the container's position.
     * @param yPosition The Y-coordinate of the container's position.
     */
    public KillDisplay(double xPosition, double yPosition) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        initializeContainer();  // Initialize the container to hold the kill image and count
        initializeKillImage();  // Initialize the kill image (icon)
        initializeKillCountLabel();  // Initialize the label to display the kill count
    }

    /**
     * Initializes the container (HBox) for holding the kill image and the kill count label.
     */
    private void initializeContainer() {
        container = new HBox();  // Create a new HBox to hold the kill image and label
        container.setLayoutX(containerXPosition);  // Set the X-position of the container
        container.setLayoutY(containerYPosition);  // Set the Y-position of the container
        container.setSpacing(10);  // Add spacing between the image
    }

    /**
     * Initializes the image that represents kills. This image will be added to the container.
     */
    private void initializeKillImage() {
        // Create an ImageView to display the kill image and set its properties
        ImageView killImage = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource(KILL_IMAGE_NAME)).toExternalForm()));
        killImage.setFitHeight(IMAGE_HEIGHT);  // Set the height of the image
        killImage.setPreserveRatio(true);  // Maintain the aspect ratio of the image
        killImage.setTranslateY(-10);  // Move the image 10 units up to align it better
        killImage.setTranslateX(15);   // Move the image 15 units to the right to position it properly
        container.getChildren().add(killImage);  // Add the image to the container
    }

    /**
     * Initializes the label that will display the number of kills.
     */
    private void initializeKillCountLabel() {
        killCountLabel = new Label("0");  // Initialize the label with the default value of 0 kills
        killCountLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");  // Style the label with white text and font size
        container.getChildren().add(killCountLabel);  // Add the label to the container
    }

    /**
     * Updates the kill count displayed by the label.
     *
     * @param kills The new kill count to display.
     */
    public void updateKillCount(int kills) {
        killCountLabel.setText(String.valueOf(kills));  // Set the text of the label to the new kill count
    }

    /**
     * Resets the kill count to 0.
     */
    public void resetKillCount() {
        updateKillCount(0);  // Reset the kill count by calling updateKillCount with 0
    }

    /**
     * Retrieves the container (HBox) containing the kill image and the kill count label,
     * so it can be added to the scene.
     *
     * @return The HBox container with the kill image and count label.
     */
    public HBox getContainer() {
        return container;  // Return the container holding the kill image and label
    }
}
