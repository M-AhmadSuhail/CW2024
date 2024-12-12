package com.example.demo.UI;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.util.Duration;

/**
 * The CollisionEffect class manages the visual display of collision effects (such as explosions) in the game.
 * It uses JavaFX to render an explosion image at a specified location and applies a fade-out animation.
 */
public class CollisionEffect {
    // Root group to add and remove visual elements
    private final Group root;

    /**
     * Constructor to initialize the CollisionEffect with the root node.
     *
     * @param root the Group node where the collision effect will be displayed
     */
    public CollisionEffect(Group root) {
        this.root = root;
    }

    /**
     * Displays a collision effect (explosion) at the specified coordinates.
     * The effect includes scaling the explosion image and fading it out over 2 seconds.
     *
     * @param x the X-coordinate for the center of the explosion
     * @param y the Y-coordinate for the center of the explosion
     */
    public void displayEffect(double x, double y) {
        System.out.println("Displaying explosion effect at: (" + x + ", " + y + ")");//Debugging

        // Load the explosion image from resources
        Image explosionImage = new Image(getClass().getResource("/com/example/demo/images/explosion2.png").toExternalForm());
        ImageView collisionImage = new ImageView(explosionImage);

        // Scale down the image to 50% of its original size
        collisionImage.setFitWidth(explosionImage.getWidth() * 0.5);
        collisionImage.setFitHeight(explosionImage.getHeight() * 0.5);

        // Position the image so that it is centered at (x, y)
        collisionImage.setX(x - collisionImage.getFitWidth() / 2);
        collisionImage.setY(y - collisionImage.getFitHeight() / 2);

        // Ensure the image is added to the scene graph on the JavaFX Application Thread
        Platform.runLater(() -> root.getChildren().add(collisionImage));

        // Create a fade-out animation for the explosion image
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), collisionImage);
        fadeTransition.setFromValue(1.0); // Start fully opaque
        fadeTransition.setToValue(0.0); // Fade to fully transparent
        fadeTransition.setOnFinished(e ->
                // Remove the image from the scene graph after the fade animation finishes
                Platform.runLater(() -> root.getChildren().remove(collisionImage))
        );
        fadeTransition.play(); // Start the animation
    }
}
