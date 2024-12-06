package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 50;
	private static final int X_OFFSET = 8;  // Adjust how far to the right
	private static final int Y_OFFSET = 15;  // Adjust how far down

	public ShieldImage(double screenWidth, double screenHeight) {
		// Set the position to the top-right corner, adjusted with offsets
		this.setLayoutX(screenWidth - SHIELD_SIZE - X_OFFSET);  // Position it to the right
		this.setLayoutY(Y_OFFSET);  // Position it slightly down from the top

		// Load the shield image
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);  // Initially, the shield is not visible
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	public void showShield() {
		this.setVisible(true);
		toFront();  // Bring the shield image to the front to ensure it's not hidden
	}

	public void hideShield() {
		this.setVisible(false);
	}
}
