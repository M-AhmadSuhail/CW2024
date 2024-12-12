package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * The GameOverImage class represents an image displayed when the game ends.
 * It extends the JavaFX `ImageView` and provides functionality for managing the "Game Over" image.
 */
public class GameOverImage extends ImageView {

	// Constants for the image file path and dimensions
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";
	private static final int HEIGHT = 400;
	private static final int WIDTH = 600;

	/**
	 * Constructor to initialize the GameOverImage at a specific position.
	 *
	 * @param xPosition the X-coordinate for positioning the image
	 * @param yPosition the Y-coordinate for positioning the image
	 */
	public GameOverImage(double xPosition, double yPosition) {
		// Load and set the "Game Over" image
		setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false); // Initially hidden
		this.setFitHeight(HEIGHT); // Set the height
		this.setFitWidth(WIDTH); // Set the width
		this.setLayoutX(xPosition); // Set the X-coordinate
		this.setLayoutY(yPosition); // Set the Y-coordinate
	}

	/**
	 * Makes the "Game Over" image visible on the screen.
	 */
	public void showGameOver() {
		this.setVisible(true);
	}
}
