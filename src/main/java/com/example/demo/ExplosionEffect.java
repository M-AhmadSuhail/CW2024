package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;

public class ExplosionEffect {

    private final String imagePath;
    private final double width;
    private final double height;
    private final double fadeDuration;
    private final AudioClip explosionSound; // Using AudioClip for sound handling
    private Image explosionImage;

    public ExplosionEffect(String imagePath, double width, double height, double fadeDuration, AudioClip explosionSound) {
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
        this.fadeDuration = fadeDuration;
        this.explosionSound = explosionSound;

        // Preload image resource (called once in the constructor)
        this.explosionImage = loadImage(imagePath);
    }

    private Image loadImage(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl == null) {
            System.err.println("Error: Image resource not found: " + imagePath);
            return null; // Return null if not found
        }
        return new Image(imageUrl.toExternalForm());
    }

    public void createEffect(double x, double y, Group root) {
        if (explosionImage == null) {
            System.err.println("Error: Image resource not loaded properly.");
            return; // Exit early if resources are not loaded
        }

        // Reuse the image object for the explosion effect
        ImageView explosion = new ImageView(explosionImage);
        explosion.setFitWidth(width);
        explosion.setFitHeight(height);
        explosion.setLayoutX(x);
        explosion.setLayoutY(y);

        // Add explosion image to the scene
        root.getChildren().add(explosion);

        // Play the sound effect if available
        playSoundEffect();

        // Create and play the fade-out animation
        FadeTransition fadeOut = createFadeAnimation(explosion, root);
        fadeOut.play();
    }

    private void playSoundEffect() {
        if (explosionSound != null) {
            explosionSound.play();
        }
    }

    private FadeTransition createFadeAnimation(ImageView explosion, Group root) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeDuration), explosion);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> root.getChildren().remove(explosion));
        return fadeOut;
    }

}
