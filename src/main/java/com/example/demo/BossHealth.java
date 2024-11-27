package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BossHealth {

    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
    private static final int HEART_SIZE = 30;  // Size of the heart image
    private static final double SPACING = 5;  // Spacing between elements in the HBox

    private final HBox container;  // Holds the heart image and health counter
    private final Label healthCounter;  // Label to display the boss health count
    private int health;  // Current health of the boss
    private final int initialHealth; // Store the initial health for resetting

    /**
     * Constructor to initialize the health display.
     * @param xPosition The x-coordinate for the display.
     * @param yPosition The y-coordinate for the display.
     * @param initialHealth The initial health value of the boss.
     */
    public BossHealth(double xPosition, double yPosition, int initialHealth) {
        this.initialHealth = initialHealth; // Save the initial health value
        this.health = initialHealth;
        this.container = new HBox(SPACING);  // Create HBox with specified spacing

        // Set position of the container
        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);

        // Add the heart image
        ImageView heartImage = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
        heartImage.setFitHeight(HEART_SIZE);  // Set height of the heart image
        heartImage.setFitWidth(HEART_SIZE);  // Set width of the heart image
        container.getChildren().add(heartImage);

        // Add the health counter label
        this.healthCounter = new Label("x " + health);  // Display initial health
        healthCounter.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");  // Set font size and color
        container.getChildren().add(healthCounter);
    }

    /**
     * Updates the displayed health when the boss is hit.
     * @param newHealth The updated health value.
     */
    public void updateHealth(int newHealth) {
        this.health = newHealth;  // Update health
        healthCounter.setText("x " + health);  // Update label text
    }

    /**
     * Resets the boss's health to its initial value.
     */
    public void resetHealth() {
        this.health = initialHealth;  // Reset health to the initial value
        healthCounter.setText("x " + health);  // Update label text
    }

    /**
     * Returns the container holding the heart image and health counter.
     * @return The HBox container.
     */
    public HBox getContainer() {
        return container;
    }
}
