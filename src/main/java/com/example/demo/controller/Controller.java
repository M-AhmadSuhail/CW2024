package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import com.example.demo.LevelParent;
import com.example.demo.GamePause;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;
	private GamePause gamePause;
	private LevelParent currentLevel;
	private boolean isPaused = false; // Track pause state

	public Controller(Stage stage) {
		this.stage = stage;
		initializeGamePause();
	}

	public void launchGame() {
		try {
			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
		} catch (Exception e) {
			handleException(e);
		}
	}

	private void initializeGamePause() {
		gamePause = new GamePause(stage,
				this::resumeGame, // Resume game logic
				() -> System.exit(0), // Exit game logic
				() -> System.out.println("Settings Opened")); // Settings logic placeholder
	}

	private void goToLevel(String className) {
		try {
			System.out.println("Attempting to load level: " + className); // Debugging log
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			currentLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

			// Ensure the LevelParent instance is set up correctly
			currentLevel.addObserver(this);
			Scene scene = currentLevel.initializeScene();
			scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress); // Pause key listener
			stage.setScene(scene);
			currentLevel.startGame();
		} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
				 | IllegalAccessException | InvocationTargetException e) {
			handleException(e);
		}
	}

	private void handleKeyPress(KeyEvent event) {
		if (event.getCode() == KeyCode.P) {
			if (isPaused) {
				resumeGame();
			} else {
				pauseGame();
			}
		}
	}

	private void pauseGame() {
		System.out.println("Game Paused");
		isPaused = true;
		if (currentLevel != null) {
			currentLevel.pauseGame(); // Custom pause logic in the LevelParent class
		}
		gamePause.show();
	}

	private void resumeGame() {
		System.out.println("Game Resumed");
		isPaused = false;
		if (currentLevel != null) {
			currentLevel.resumeGame(); // Custom resume logic in the LevelParent class
		}
		gamePause.hide(); // Close the pause menu using the instance
	}

	@Override
	public void update(Observable observable, Object levelClassName) {
		if (levelClassName instanceof String) {
			try {
				System.out.println("Transitioning to next level: " + levelClassName); // Debugging log
				goToLevel((String) levelClassName);
			} catch (Exception e) {
				handleException(e);
			}
		} else {
			System.out.println("Received unexpected update: " + levelClassName);
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
