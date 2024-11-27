package com.example.demo.controller;

import com.example.demo.LevelTwo;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demo.LevelView;

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

			// Initialize the root group for the LevelView
			Group root = new Group();

			// Create the LevelView instance, you can adjust the values as needed for hearts and kills
			LevelView levelView = new LevelView(root, 3, 0);

			// Create an instance of LevelTwo and pass it to the controller
			LevelTwo levelTwo = new LevelTwo(SCREEN_HEIGHT, SCREEN_WIDTH);

			// Initialize the scene with the root group
			Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

			// Set the scene on the stage
			stage.setScene(scene);

			// Initialize the controller and pass the stage and the levelTwo instance
			Controller controller = new Controller(stage, levelView);

			// Launch the game
			controller.launchGame();

			// Show the stage (window)
			stage.show();

		} catch (Exception e) {
			// Catch any initial  ization errors and print them
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
