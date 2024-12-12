package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The WinImage class represents the image displayed when the player wins the game.
 * It is initially invisible and can be shown by calling the `showWinImage` method.
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";  // Path to the win image
	private static final int HEIGHT = 500;  // Height of the win image
	private static final int WIDTH = 600;  // Width of the win image

	/**
	 * Constructs a WinImage object and positions it at the specified coordinates.
	 * The image is initially invisible and can be shown later.
	 *
	 * @param xPosition The X coordinate to position the image on the screen.
	 * @param yPosition The Y coordinate to position the image on the screen.
	 */
	public WinImage(double xPosition, double yPosition) {
		// Load the win image from the specified resource path
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);  // Initially, the image is not visible
		this.setFitHeight(HEIGHT);  // Set the height of the win image
		this.setFitWidth(WIDTH);  // Set the width of the win image
		this.setLayoutX(xPosition);  // Set the X position of the image
		this.setLayoutY(yPosition);  // Set the Y position of the image
	}

	/**
	 * Makes the win image visible on the screen.
	 */
	public void showWinImage() {
		this.setVisible(true);  // Make the win image visible
	}
}
