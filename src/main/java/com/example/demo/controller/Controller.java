package com.example.demo.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.*;

/**
 * The Controller class is responsible for managing the flow of the game, transitioning between levels,
 * handling key events, and managing the pause and resume functionality. It interacts with the LevelParent,
 * LevelView, and other game-related components.
 */
public class Controller implements Observer {

	public static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.Levels.LevelOne";

	private final Stage stage;
	private final LevelView levelView;
	private LevelParent currentLevel;
	protected boolean isPaused = false; // Tracks the pause state
	protected Timeline timeline;

	/**
	 * Constructs a new Controller object.
	 *
	 * @param stage the primary stage for the application.
	 * @param levelView the view of the current level.
	 */
	public Controller(Stage stage, LevelView levelView) {
		this.stage = stage;
		this.levelView = levelView;
		this.timeline = new Timeline(); // Correct timeline initialization
		this.levelView.setController(this);

		LevelParent.setController(this);
	}

	/**
	 * Launches the game by displaying the stage and starting the first level.
	 *
	 * @throws ClassNotFoundException if the level class cannot be found.
	 * @throws NoSuchMethodException if the level class constructor is not found.
	 * @throws InstantiationException if the level class cannot be instantiated.
	 * @throws IllegalAccessException if the constructor is inaccessible.
	 * @throws IllegalArgumentException if the constructor is called with invalid arguments.
	 * @throws InvocationTargetException if an exception is thrown by the level's constructor.
	 */
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

	/**
	 * Transitions to a specified level by using reflection to instantiate the level class.
	 *
	 * @param className the name of the level class to transition to.
	 * @throws ClassNotFoundException if the class cannot be found.
	 * @throws NoSuchMethodException if the constructor of the level class cannot be found.
	 * @throws SecurityException if there is a security issue during reflection.
	 * @throws InstantiationException if the class cannot be instantiated.
	 * @throws IllegalAccessException if there is illegal access to the constructor.
	 * @throws IllegalArgumentException if the constructor is called with invalid arguments.
	 * @throws InvocationTargetException if an exception is thrown by the level's constructor.
	 */
	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.addObserver(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.initializeLevel();
	}

	/**
	 * Displays a pause popup to the user with an option to resume the game.
	 */
	private void showPausePopup() {
		JFrame frame = new JFrame("Game Paused");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(300, 150);
		frame.setLayout(new BorderLayout());

		JLabel message = new JLabel("Game is Paused", SwingConstants.CENTER);
		frame.add(message, BorderLayout.CENTER);

		JButton resumeButton = new JButton("Resume");
		frame.add(resumeButton, BorderLayout.SOUTH);

		// Action listener for resume button
		resumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				resumeGame();
			}
		});

		// Center the frame on the screen
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Pauses the game and the main game loop. Displays the pause menu.
	 */
    protected void pauseGame() {
		System.out.println("Game Paused");
		isPaused = true;

		// Pause the main timeline (game loop)
		timeline.pause();

		// Pause any other background game processes
		if (currentLevel != null) {
			currentLevel.pauseGame();  // Pauses the level-specific game logic
		}
	}

	/**
	 * Resumes the game and the main game loop. Hides the pause menu.
	 */
    protected void resumeGame() {
		System.out.println("Game Resumed");
		isPaused = false;

		// Resume the main timeline (game loop)
		timeline.play();

		// Resume level-specific game logic
		if (currentLevel != null) {
			currentLevel.resumeGame();  // Resumes the level-specific game logic
		}
	}

	/**
	 * Updates the controller based on notifications from the observed object.
	 * This is typically used for transitioning between levels.
	 *
	 * @param observable the observable object that triggered the update.
	 * @param arg an argument passed by the observable.
	 */
	public void update(Observable observable, Object arg) {
		try {
			goToLevel((String) arg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Restarts the game by playing the timeline again and re-launching the game.
	 */
	public void restartGame() {
		System.out.println("Game Restarting...");
		timeline.play();
		launchGame();  // Re-launch the game
	}

	/**
	 * Closes the game and exits the application.
	 */
    protected void closeGame() {
		System.out.println("Exiting game...");
		System.exit(0);
	}

	/**
	 * Placeholder method for opening the settings menu.
	 * This method could be expanded in the future to manage game settings.
	 */
	private void openSettings() {
		System.out.println("Settings menu opened (placeholder)");
	}
}