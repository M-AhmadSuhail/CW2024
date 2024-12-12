package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;

/**
 * The HeartDisplay class represents a UI component for displaying a set number of hearts
 * (e.g., lives) in a horizontal layout. It supports adding and removing hearts dynamically.
 */
public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";  // Path to heart image
	private static final int HEART_HEIGHT = 50;  // Height of each heart image
	private static final int INDEX_OF_FIRST_ITEM = 0;  // Index used to remove the first heart

	private HBox container;  // The container (HBox) that holds the heart images
	private final double containerXPosition;  // The x-coordinate for positioning the container
	private final double containerYPosition;  // The y-coordinate for positioning the container
	private final int numberOfHeartsToDisplay;  // The initial number of hearts to display

	/**
	 * Constructor to initialize the HeartDisplay with its position and the number of hearts.
	 *
	 * @param xPosition        The x-coordinate of the container on the screen.
	 * @param yPosition        The y-coordinate of the container on the screen.
	 * @param heartsToDisplay  The number of hearts to display initially.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();  // Initialize the container
		initializeHearts();  // Initialize the hearts inside the container
	}

	/**
	 * Initializes the container (HBox) for the hearts and positions it on the screen.
	 * Sets the layout of the container to the provided x and y positions.
	 */
	private void initializeContainer() {
		container = new HBox();  // Create a new HBox to hold the hearts
		container.setLayoutX(containerXPosition);  // Set the x position of the container
		container.setLayoutY(containerYPosition);  // Set the y position of the container
	}

	/**
	 * Initializes the hearts in the container based on the specified number of hearts.
	 * Calls the addHeartToContainer method for each heart to display.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			addHeartToContainer();  // Add a heart to the container for each heart to display
		}
	}

	/**
	 * Adds a single heart image to the container.
	 * Creates an ImageView with the heart image, sets its size, and adds it to the container.
	 */
	private void addHeartToContainer() {
		ImageView heart = new ImageView(new Image(
				Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));  // Load the heart image
		heart.setFitHeight(HEART_HEIGHT);  // Set the height of the heart
		heart.setPreserveRatio(true);  // Maintain the aspect ratio of the heart image
		container.getChildren().add(heart);  // Add the heart ImageView to the container
	}

	/**
	 * Removes a heart from the container, if any hearts are available.
	 * If there are hearts in the container, the first one (at index 0) is removed.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);  // Remove the first heart from the container
		}
	}

	/**
	 * Resets the container to its original number of hearts.
	 * Clears the existing hearts and reinitializes the hearts in the container.
	 */
	public void resetHearts() {
		container.getChildren().clear();  // Remove all hearts from the container
		initializeHearts();  // Reinitialize the hearts based on the original number
	}

	/**
	 * Retrieves the container (HBox) containing the hearts for adding to the UI scene.
	 *
	 * @return The HBox container holding the hearts.
	 */
	public HBox getContainer() {
		return container;  // Return the container holding the hearts
	}
}
