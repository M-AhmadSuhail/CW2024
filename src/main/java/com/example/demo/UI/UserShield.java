package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * The UserShield class represents the shield icon for the player in the game.
 * It is displayed at the top-right corner of the screen and can be shown or hidden.
 * The shield image is initially invisible and can be toggled using methods.
 */
public class UserShield extends ImageView {

    private static final String IMAGE_NAME = "/com/example/demo/images/UserSheild.png";  // Path to the shield image
    private static final int SHIELD_SIZE = 100;  // Size of the shield image (width and height)
    private static final int X_OFFSET = 8;  // Horizontal offset to adjust the shield's position
    private static final int Y_OFFSET = 15;  // Vertical offset to adjust the shield's position

    /**
     * Constructs a UserShield object and positions it in the top-right corner of the screen.
     * The shield image is initially invisible.
     *
     * @param screenWidth The width of the screen to determine the position of the shield.
     * @param screenHeight The height of the screen (used for Y positioning).
     */
    public UserShield(double screenWidth, double screenHeight) {
        // Check if the resource path is correct
        URL shieldImageUrl = getClass().getResource(IMAGE_NAME);
        if (shieldImageUrl == null) {
            System.out.println("Shield image not found at: " + IMAGE_NAME);  // Log an error if the image is not found
        } else {
            // Set the position to the top-right corner, adjusted with offsets
            this.setLayoutX(screenWidth - SHIELD_SIZE - X_OFFSET);  // Position it to the right
            this.setLayoutY(Y_OFFSET);  // Position it slightly down from the top

            // Load the shield image from the specified URL
            this.setImage(new Image(shieldImageUrl.toExternalForm()));
            this.setVisible(false);  // Initially, the shield is not visible
            this.setFitHeight(SHIELD_SIZE);  // Set the height of the shield image
            this.setFitWidth(SHIELD_SIZE);  // Set the width of the shield image
        }
    }

    /**
     * Makes the shield image visible and brings it to the front of the screen.
     */
    public void showShield() {
        this.setVisible(true);  // Make the shield visible
        toFront();  // Bring the shield image to the front to ensure it's not hidden by other elements
    }

    /**
     * Hides the shield image from the screen.
     */
    public void hideShield() {
        this.setVisible(false);  // Hide the shield image
    }
}