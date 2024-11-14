package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;

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
		try {
			System.out.println("Attempting to load level: " + className); // Debugging log
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());

			// Ensure the LevelParent instance is set up correctly
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();
		} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
				 | IllegalAccessException | InvocationTargetException e) {
			handleException(e);
		}
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
