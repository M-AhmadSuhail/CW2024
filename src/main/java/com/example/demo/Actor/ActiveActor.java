package com.example.demo.Actor;

import javafx.scene.image.*;
import java.util.Objects;

/**
 * Represents an abstract active actor within the application.
 * This class extends {@link ImageView} and provides basic functionalities
 * for managing an actor's image and position in a JavaFX scene.
 */
public abstract class ActiveActor extends ImageView {

	/**
	 * Base location of image resources relative to the application's classpath.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Constructs an ActiveActor with the specified image, size, and initial position.
	 *
	 * @param imageName     the name of the image file for the actor.
	 * @param imageHeight   the height to display the image, preserving its aspect ratio.
	 * @param initialXPos   the initial horizontal position of the actor.
	 * @param initialYPos   the initial vertical position of the actor.
	 * @throws NullPointerException if the specified image resource cannot be found.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		// Load the image resource and set it to the actor.
		this.setImage(new Image(Objects.requireNonNull(
				getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));

		// Set the initial position of the actor.
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);

		// Set the size of the image while preserving its aspect ratio.
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * Abstract method to be implemented by subclasses to define how the actor's
	 * position updates over time.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by a specified distance.
	 *
	 * @param horizontalMove the distance to move the actor along the X-axis.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by a specified distance.
	 *
	 * @param verticalMove the distance to move the actor along the Y-axis.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
