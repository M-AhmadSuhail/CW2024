package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import com.example.demo.LevelParent;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;
	private String currentLevelName;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() {
		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			handleException(e);
		}
	}

	private void goToLevel(String className) {
		if (className.equals(currentLevelName)) {
			System.out.println("Already on level: " + className); // Debugging log
			return;
		}

		try {
			System.out.println("Attempting to load level: " + className); // Debugging log
			currentLevelName = className;

			// Display level name before starting the level
			displayLevelName(className);

			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();

			// Create a StackPane for the overlay (level name)
			StackPane overlayPane = new StackPane();
			overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // Semi-transparent black

			// Create a Label for the level name
			Label messageLabel = new Label("Playing: " + className.substring(className.lastIndexOf('.') + 1));
			messageLabel.setTextFill(Color.WHITE);
			messageLabel.setFont(new Font("Arial", 36));
			overlayPane.getChildren().add(messageLabel);

			// Create the scene with the overlay
			Scene overlayScene = new Scene(overlayPane, stage.getWidth(), stage.getHeight());

			// Show the overlay scene for 2 seconds
			stage.setScene(overlayScene);

			// Delay switching to the actual game scene
			PauseTransition delay = new PauseTransition(Duration.seconds(2));
			delay.setOnFinished(event -> {
				stage.setScene(scene); // Switch to the actual game scene
				myLevel.startGame(); // Start the game
			});
			delay.play();
		} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
				 | IllegalAccessException | InvocationTargetException e) {
			handleException(e);
		}
	}

	private void displayLevelName(String className) {
		String levelDisplayName = "Playing: " + className.substring(className.lastIndexOf('.') + 1);

		// Create the overlay pane for the level name (no background color)
		StackPane messagePane = new StackPane();

		// Create a Label for the level name
		Label messageLabel = new Label(levelDisplayName);
		messageLabel.setTextFill(Color.WHITE);
		messageLabel.setFont(new Font("Arial", 36));
		messageLabel.setStyle("-fx-padding: 20px;"); // Optional padding
		messagePane.getChildren().add(messageLabel);

		// Add the messagePane to the existing gameplay scene
		Scene currentScene = stage.getScene();
		if (currentScene != null) {
			StackPane gameRoot = (StackPane) currentScene.getRoot();
			gameRoot.getChildren().add(messagePane); // Add the level name overlay on top of the game scene
		}

		// Add fade-out animation for the level name
		PauseTransition fadeOut = new PauseTransition(Duration.seconds(2));
		fadeOut.setOnFinished(event -> {
			// Remove the overlay once the fade-out is complete
			messagePane.getChildren().clear();
		});
		fadeOut.play();
	}
	@Override
	public void update(Observable observable, Object levelClassName) {
		System.out.println("Observer received update. Transitioning to: " + levelClassName);
		if (levelClassName instanceof String) {
			goToLevel((String) levelClassName);
		} else {
			System.err.println("Unexpected update received: " + levelClassName);
		}
	}

	private void handleException(Exception e) {
		Throwable cause = e instanceof InvocationTargetException ? e.getCause() : e;
		if (cause != null) {
			cause.printStackTrace(); // Log the root cause
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error: " + cause.getMessage());
			alert.show();
		}
	}
}
