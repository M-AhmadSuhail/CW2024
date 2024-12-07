package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.LevelController.LevelParent;
import com.example.demo.LevelController.LevelView;
import com.example.demo.UI.GamePause;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller implements Observer {

	public static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.Levels.LevelOne";

	private final Stage stage;
	private final LevelView levelView;
	private final GamePause gamePause;
	private LevelParent currentLevel;
	private boolean isPaused = false; // Tracks the pause state
	private final Timeline timeline;

	public Controller(Stage stage, LevelView levelView) {
		this.stage = stage;
		this.levelView = levelView;
		this.timeline = new Timeline(); // Correct timeline initialization
		this.levelView.setController(this);

		this.gamePause = new GamePause(stage, this::resumeGame, this::closeGame, this::openSettings, timeline);

		LevelParent.setController(this);
	}

	public void launchGame() {
		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException |
				 InstantiationException | IllegalAccessException | IllegalArgumentException |
				 InvocationTargetException e) {
			// Handle exceptions gracefully, log, or show an error to the user
			System.err.println("Error launching the game: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Transition to a specified level using reflection
	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.addObserver(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress); // Add key event handlers
	}

	// Handle key press events for pausing and restarting
	private void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.P) { // Pause/unpause the game
			if (isPaused) {
				resumeGame();
			} else {
				pauseGame();
			}
		} else if (event.getCode() == KeyCode.R) { // Restart the game
			restartGame();
		}
	}

	// Pause the game and show the pause menu
	private void pauseGame() {
		System.out.println("Game Paused");
		isPaused = true;

		// Pause the main timeline (game loop)
		timeline.pause();

		// Pause any other background game processes
		if (currentLevel != null) {
			currentLevel.pauseGame();  // Pauses the level-specific game logic
		}

		// Optionally, stop other background tasks here (e.g., music, animations, etc.)

		// Show the pause menu
		gamePause.show();
	}

	private void resumeGame() {
		System.out.println("Game Resumed");
		isPaused = false;

		// Resume the main timeline (game loop)
		timeline.play();

		// Resume level-specific game logic
		if (currentLevel != null) {
			currentLevel.resumeGame();  // Resumes the level-specific game logic
		}

		// Resume other game processes (e.g., background music, animations, etc.)
		if (currentLevel != null) {
			currentLevel.resumeGame();  // Ensure animations or updates are resumed
		}

		// Hide the pause menu
		gamePause.hide();
	}

	public void update(Observable observable, Object arg) {
		try {
			goToLevel((String) arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restartGame() {
		System.out.println("Game Restarting...");
		timeline.play();
		launchGame();  // Re-launch the game
	}

	// Handle closing the game
	private void closeGame() {
		System.out.println("Exiting game...");
		System.exit(0);
	}

	// Placeholder for opening settings
	private void openSettings() {
		System.out.println("Settings menu opened (placeholder)");
	}
}