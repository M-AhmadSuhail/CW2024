package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.Region;

import java.util.Objects;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
//		setBackgroundImage(screenWidth, screenHeight); // Set the background image size based on screen dimensions
	}

//	private void setBackgroundImage(double screenWidth, double screenHeight) {
//		// Create an Image object from the background image file
//		Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_NAME)));
//
//		// Create an ImageView to scale the image according to the screen size
//		ImageView backgroundImageView = new ImageView(backgroundImage);
//		backgroundImageView.setFitWidth(screenWidth);  // Set the width to match screen width
//		backgroundImageView.setFitHeight(screenHeight);  // Set the height to match screen height
//		backgroundImageView.setPreserveRatio(false);  // Don't preserve aspect ratio (fill the entire screen)
//
//		// Add the ImageView as the background
//		getRoot().getChildren().add(0, backgroundImageView); // Add to the root at the bottom (background)
//	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}
}
