package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The ShieldImage class represents a shield icon displayed on the screen.
 * It is positioned at the top-right corner of the screen and can be shown or hidden.
 * The shield image is initially invisible and can be toggled via methods.
 */
public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";  // Path to the shield image
	private static final int SHIELD_SIZE = 50;  // Size of the shield image (width and height)
	private static final int X_OFFSET = 8;  // Horizontal offset to adjust the shield's position
	private static final int Y_OFFSET = 15;  // Vertical offset to adjust the shield's position

	/**
	 * Constructs a ShieldImage object and positions it in the top-right corner of the screen.
	 * The shield image is initially invisible.
	 *
	 * @param screenWidth The width of the screen to determine the position of the shield.
	 * @param screenHeight The height of the screen (used for Y positioning).
	 */
	public ShieldImage(double screenWidth, double screenHeight) {
		// Set the position to the top-right corner, adjusted with offsets
		this.setLayoutX(screenWidth - SHIELD_SIZE - X_OFFSET);  // Position it to the right
		this.setLayoutY(Y_OFFSET);  // Position it slightly down from the top

		// Load the shield image from the specified path
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);  // Initially, the shield is not visible
		this.setFitHeight(SHIELD_SIZE);  // Set the height of the shield image
		this.setFitWidth(SHIELD_SIZE);  // Set the width of the shield image
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
