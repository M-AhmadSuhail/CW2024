package com.example.demo.controller;

import com.example.demo.UI.MainMenuScreen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class is the entry point of the application. It sets up the initial stage (window),
 * initializes the main menu screen, and launches the game. This class extends the `Application` class
 * to work with JavaFX for creating the graphical user interface.
 */
public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";

	/**
	 * Initializes and shows the main menu screen when the application is launched.
	 *
	 * @param stage the primary stage for this application.
	 */
	@Override
	public void start(Stage stage) {
		try {
			// Set up the stage (window)
			stage.setTitle(TITLE);
			stage.setResizable(false);
			stage.setWidth(SCREEN_WIDTH);
			stage.setHeight(SCREEN_HEIGHT);

			// Initialize and show the main menu
			MainMenuScreen mainMenuScreen = new MainMenuScreen();
			mainMenuScreen.start(stage);  // Display the main menu

		} catch (Exception e) {
			// Catch any initialization errors and print them
			e.printStackTrace();
			System.out.println("An error occurred while launching the game: " + e.getMessage());
		}
	}

	/**
	 * Returns the screen width for the game window.
	 *
	 * @return the width of the screen.
	 */
	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	/**
	 * Returns the screen height for the game window.
	 *
	 * @return the height of the screen.
	 */
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	/**
	 * The main method that launches the application.
	 * It is the entry point to the JavaFX application.
	 *
	 * @param args command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		launch();
	}
}