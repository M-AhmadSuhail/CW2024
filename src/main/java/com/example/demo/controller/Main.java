package com.example.demo.controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";

	@Override
	public void start(Stage stage) {
		try {
			stage.setTitle(TITLE);
			stage.setResizable(false);
			stage.setWidth(SCREEN_WIDTH);
			stage.setHeight(SCREEN_HEIGHT);

			// Initialize the controller and launch the game
			Controller myController = new Controller(stage);
			myController.launchGame();

		} catch (Exception e) {
			// If any exceptions occur during initialization, display a basic error message.
			e.printStackTrace();
			System.out.println("An error occurred while launching the game: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch();
	}
}