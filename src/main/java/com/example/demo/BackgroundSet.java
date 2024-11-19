package com.example.demo;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.Objects;

public class BackgroundSet {
    private final ImageView background;

    public BackgroundSet(String backgroundImageName, double screenHeight, double screenWidth, Group root) {
        // Load the background image
        URL imageUrl = getClass().getResource(backgroundImageName);
        if (imageUrl == null) {
            throw new IllegalArgumentException("Background image not found: " + backgroundImageName);
        }

        this.background = new ImageView(new Image(imageUrl.toExternalForm()));

        // Set up the background properties
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setPreserveRatio(false);
        background.setSmooth(true);

        // Add the background to the root node
        root.getChildren().add(background);
    }

    public void setupKeyBindings(UserPlane user) {
        background.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            switch (key) {
                case UP -> user.moveUp();
                case DOWN -> user.moveDown();
                case SPACE -> user.fireProjectile(); // Fire a projectile
                default -> {
                } // No action for other keys
            }
        });

        background.setOnKeyReleased(e -> {
            KeyCode key = e.getCode();
            if (key == KeyCode.UP || key == KeyCode.DOWN) {
                user.stop();
            }
        });
    }

    public ImageView getBackground() {
        return background;
    }
}
