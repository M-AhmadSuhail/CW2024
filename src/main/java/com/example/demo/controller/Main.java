package com.example.demo.controller;

import com.example.demo.MainMenuScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";

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

	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public static void main(String[] args) {
		launch();
	}
}
