package com.example.demo.LevelController;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.UI.*;
import com.example.demo.controller.Main;
import javafx.application.Platform;
import javafx.scene.control.Label;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.MusicController.Music;
import com.example.demo.MusicController.Bgm;

import com.example.demo.Plane.FighterPlane;
import com.example.demo.Plane.UserPlane;
import com.example.demo.controller.Controller;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * The abstract class representing a level in the game. It contains methods and properties for initializing
 * and managing the level's actors, projectiles, enemies, user plane, and background. It is also responsible for
 * the level's progression and game-over checks.
 */
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private static final int PROJECTILE_COOLDOWN = 200; // Cooldown duration in milliseconds

	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private static Controller controller;
	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;
	private final String MESSAGE_ON_SCREEN;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private int currentNumberOfEnemies;
	protected String currentLevelClassName;
	protected final LevelView levelView;
	private final KillDisplay killDisplay;
	protected abstract LevelView instantiateLevelView();

	// Pause state
	protected boolean isPaused = false;
	private boolean isCooldown = false; // Cooldown state
	private Bgm bgm;
	private Music soundManager;
	private String collisionSoundFile;
	private CollisionEffect collisionEffect;
	// GamePause class reference
	private GamePause gamePause;
	private UserShield userShield;

	/**
	 * Constructs a new LevelParent instance.
	 *
	 * @param backgroundImageName the background image for the level.
	 * @param screenHeight the height of the screen.
	 * @param screenWidth the width of the screen.
	 * @param playerInitialHealth the initial health of the player.
	 * @param messageOnScreen the message displayed on the screen.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, String messageOnScreen) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		if (levelView == null) {
			throw new IllegalStateException("LevelView is not properly instantiated.");
		}
		this.currentNumberOfEnemies = 0;
		this.MESSAGE_ON_SCREEN = messageOnScreen;  // Set the message here
		this.killDisplay = new KillDisplay(10, 70); // Adjust position as needed
		root.getChildren().add(killDisplay.getContainer());
		initializeTimeline();
		friendlyUnits.add(user);
		soundManager = new Music();
		userShield = new UserShield(screenWidth, screenHeight);
		root.getChildren().add(userShield);
		collisionEffect = new CollisionEffect(root);

		// Initialize the GamePause instance
		gamePause = new GamePause(this::resumeGame, this::restartGame, this::exitGame);
	}

	/**
	 * Sets the game controller.
	 *
	 * @param gameController the controller for the game.
	 */
	public static void setController(Controller gameController) {
		controller = gameController;
	}

	/**
	 * Abstract method to initialize the friendly units for the level.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Abstract method to check if the game is over.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Abstract method to spawn enemy units in the level.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Initializes the scene for the level by setting up background, friendly units, and UI elements.
	 *
	 * @return the initialized scene.
	 */
	public Scene initializeScene() {
		// Initialize the Bgm instance and play background music
		// Initialize other components of the scene
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showKillDisplay();
		levelView.entryMessage(MESSAGE_ON_SCREEN);
		if (bgm == null) {
			bgm = new Bgm(0.5);  // Initialize bgm with volume
		}
		bgm.playBGM("background.mp3");
		return scene;

	}

	/**
	 * Starts the level by playing the timeline animation.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * Transitions to the next level.
	 *
	 * @param nextLevelClassName the class name of the next level.
	 */
	public void goToNextLevel(String nextLevelClassName) {
		timeline.stop();
		setChanged();
		notifyObservers(nextLevelClassName);
	}

	/**
	 * Updates the game scene by spawning enemies, updating actors, generating fire, and checking collisions.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();

		boolean planeCollision = handlePlaneCollisions();
		boolean userProjectileCollision = handleUserProjectileCollisions();
		boolean enemyProjectileCollision = handleEnemyProjectileCollisions();

		// Calculate enemies eliminated in this frame
		int enemiesEliminated = calculateEnemiesEliminated();

		removeAllDestroyedActors();

		// Update UI and state
		updateHitCount(userProjectileCollision, enemiesEliminated);
		updateLevelView(enemiesEliminated);
		checkIfGameOver();
	}

	/**
	 * Calculates the number of enemies that have been eliminated in the current frame.
	 *
	 * @return the number of eliminated enemies.
	 */
	private int calculateEnemiesEliminated() {
		return (int) enemyUnits.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.count();
	}


	/**
	 * Initializes the game loop with a fixed time interval.
	 * The game loop is responsible for updating the scene regularly.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * Initializes the background and sets up key event handlers for user input.
	 * Handles movement, firing projectiles, and pausing the game.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setPreserveRatio(false);
		background.setSmooth(true);

		// Event handler for key press actions to move the user plane and fire projectiles
		background.setOnKeyPressed(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP) getUser().moveUp();
			if (kc == KeyCode.DOWN) getUser().moveDown();
			if (kc == KeyCode.SPACE) fireProjectile();
			if (kc == KeyCode.P) togglePause();  // Pause/unpause the gameLoop
		});

		// Event handler for key release actions to stop the user plane's movement
		background.setOnKeyReleased(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP || kc == KeyCode.DOWN) getUser().stop();
		});

		root.getChildren().add(background);
	}

	/**
	 * Toggles the pause state of the game.
	 * If the game is paused, it will resume; otherwise, it will pause the game.
	 */
	private void togglePause() {
		if (isPaused) {
			resumeGame();
		} else {
			gamePause.showPausePopup();  // Show the pause popup
			pauseGame();  // Pause the game
		}
	}

	/**
	 * Fires a projectile from the user's plane if the cooldown period has expired.
	 * Starts the cooldown timer after firing the projectile.
	 */
	private void fireProjectile() {
		if (!isCooldown) { // Check if the cooldown is inactive
			ActiveActorDestructible projectile = user.fireProjectile();
			if (projectile != null) { // Only add the projectile if it is not null
				root.getChildren().add(projectile);
				userProjectiles.add(projectile);
				startCooldown(); // Start cooldown after firing
			}
		} else {
			System.out.println("Projectile not fired. Cooldown in effect.");
		}
	}

	/**
	 * Starts the cooldown timer for firing projectiles.
	 * The cooldown prevents the user from firing too rapidly.
	 */
	private void startCooldown() {
		isCooldown = true; // Set cooldown flag to true
		PauseTransition cooldownTimer = new PauseTransition(Duration.millis(PROJECTILE_COOLDOWN));
		cooldownTimer.setOnFinished(event -> isCooldown = false); // Reset the cooldown after the specified time
		cooldownTimer.play();
	}

	/**
	 * Generates enemy projectiles by iterating through all enemy units.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns an enemy projectile and adds it to the scene and the list of enemy projectiles.
	 *
	 * @param projectile The projectile to spawn.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * Updates all actors in the game, including user projectiles and enemy units.
	 * This method is called every frame to update their states.
	 */
	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	/**
	 * Removes all destroyed actors (friendly units, enemy units, projectiles) from the scene and the internal lists.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes all destroyed actors from the scene and the provided list.
	 *
	 * @param actors The list of actors to check and remove destroyed ones.
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors); // Remove from scene
		actors.removeAll(destroyedActors);            // Remove from list
	}

	/**
	 * Handles collisions between friendly and enemy planes.
	 * Displays collision effects if any friendly planes are destroyed.
	 *
	 * @return true if any collision occurred, false otherwise.
	 */
	private boolean handlePlaneCollisions() {
		boolean collisionOccurred = handleCollisions(friendlyUnits, enemyUnits);

		if (collisionOccurred) {
			// For each destroyed friendly actor, display a collision effect at its position
			for (ActiveActorDestructible friend : friendlyUnits) {
				if (friend.isDestroyed()) {
					double x = friend.getLayoutX() + friend.getTranslateX();
					double y = friend.getLayoutY() + friend.getTranslateY();

					// Call displayEffect on the CollisionEffect instance
					collisionEffect.displayEffect(x, y);
				}
			}
		}

		return collisionOccurred;
	}

	/**
	 * Handles collisions between user projectiles and enemy units.
	 *
	 * @return true if any collision occurred, false otherwise.
	 */
	private boolean handleUserProjectileCollisions() {
		boolean collisionOccurred = false;

		for (ActiveActorDestructible projectile : userProjectiles) {
			for (ActiveActorDestructible enemy : enemyUnits) {
				if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
					projectile.destroy(); // Destroy the projectile upon collision
					collisionOccurred = true;

					// Play collision sound
					if (collisionSoundFile != null) {
						soundManager.playSound(collisionSoundFile);
					} else {
						System.err.println("No collision sound file set for this level.");
					}

					if (((UserPlane) getUser()).isOneHitKillActive()) {
						// One-hit-kill mode: destroy the hit enemy and two additional ones
						((UserPlane) getUser()).applyOneHitKillEffect(enemy, enemyUnits, root, true); // Pass `true` for one-hit-kill mode
					} else {
						// Normal mode: apply damage and check if the enemy is destroyed
						enemy.takeDamage();
						if (enemy.isDestroyed()) {
							// Get coordinates of the destroyed enemy
							double x = enemy.getLayoutX() + enemy.getTranslateX();
							double y = enemy.getLayoutY() + enemy.getTranslateY();

							// Display collision effect
							collisionEffect.displayEffect(x, y);

							// Remove the enemy from the scene
							root.getChildren().remove(enemy);
							enemyUnits.remove(enemy);

							// Increment hit count for the user
							((UserPlane) getUser()).incrementHitCountBy(1);
						}
					}
					break; // Stop checking further collisions for this projectile
				}
			}
		}
		return collisionOccurred;
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units.
	 *
	 * @return true if any collision occurred, false otherwise.
	 */
	private boolean handleEnemyProjectileCollisions() {
		return handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * Handles collisions between two sets of actors (e.g., friendly and enemy units, projectiles).
	 * Applies damage to both actors involved in the collision and displays a collision effect at the point of impact.
	 *
	 * @param actors1 The first set of actors (e.g., user projectiles or friendly units).
	 * @param actors2 The second set of actors (e.g., enemy projectiles or enemy units).
	 * @return true if any collision occurred, false otherwise.
	 */
	private boolean handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		boolean collisionOccurred = false;
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					// Damage both actors
					actor.takeDamage();
					otherActor.takeDamage();
					collisionOccurred = true;
					if (collisionSoundFile != null) {
						soundManager.playSound(collisionSoundFile);
					} else {
						System.err.println("No collision sound file set for this level.");
					}

					// Calculate collision position (average of both actors' positions)
					double collisionX = (actor.getLayoutX() + actor.getTranslateX() +
							otherActor.getLayoutX() + otherActor.getTranslateX()) / 2;
					double collisionY = (actor.getLayoutY() + actor.getTranslateY() +
							otherActor.getLayoutY() + otherActor.getTranslateY()) / 2;

					// Display collision effect
					collisionEffect.displayEffect(collisionX, collisionY);
				}
			}
		}
		return collisionOccurred;
	}
	/**
	 * Handles the enemy penetration scenario where the enemy crosses the defense boundaries.
	 * If an enemy penetrates, it causes damage to the user and the enemy is destroyed.
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	/**
	 * Updates the level view with the number of kills and current health of the user.
	 *
	 * @param enemiesEliminated The number of enemies eliminated in the current update.
	 */
	private void updateLevelView(int enemiesEliminated) {
		levelView.removeHearts(user.getHealth()); // Update health display
		levelView.updateKills(user.getNumberOfHits()); // Update total kills in the view
		System.out.println("Level view updated: " + enemiesEliminated + " enemies eliminated.");
	}

	/**
	 * Updates the hit count of the user when a collision is detected and enemies are eliminated.
	 *
	 * @param collisionDetected A flag indicating whether a collision was detected.
	 * @param enemiesEliminated The number of enemies eliminated.
	 */
	protected void updateHitCount(boolean collisionDetected, int enemiesEliminated) {
		if (collisionDetected && enemiesEliminated > 0) {
			user.incrementHitCountBy(enemiesEliminated); // Update hit count for multiple kills
			System.out.println("User hit-count updated: " + user.getNumberOfHits());
		}
	}

	/**
	 * Checks if the enemy has penetrated the defense boundaries.
	 *
	 * @param enemy The enemy unit to check.
	 * @return true if the enemy has penetrated the defenses, false otherwise.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	/**
	 * Handles the logic for winning the game. It stops the game timeline and displays the win screen.
	 */
	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		EndGameScreen endGameMenu = new EndGameScreen("You Win!", screenWidth, screenHeight);
		attachEndGameMenu(endGameMenu);
	}

	/**
	 * Handles the logic for losing the game. It stops the game timeline and displays the game over screen.
	 */
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		EndGameScreen endGameMenu = new EndGameScreen("Oops! Try Again?", screenWidth, screenHeight);
		attachEndGameMenu(endGameMenu);
	}

	/**
	 * Attaches the end game menu and sets up action handlers for restart and exit buttons.
	 *
	 * @param endGameMenu The end game screen to attach.
	 */
	private void attachEndGameMenu(EndGameScreen endGameMenu) {
		root.getChildren().add(endGameMenu); // Add overlay last to ensure it's on top
		endGameMenu.getExitButton().setOnAction(event -> {
			System.out.println("Exiting game...");
			System.exit(0); // Exit the application
		});
		endGameMenu.getRestartButton().setOnAction(event -> {
			System.out.println("Restarting game...");
			restartGame();
		});
		endGameMenu.requestFocus(); // Ensure it receives input focus
	}

	/**
	 * Restarts the game by dynamically loading the current level.
	 */
	private void restartGame() {
		try {
			if (currentLevelClassName != null) {
				controller.goToLevel(currentLevelClassName); // Restart the current level dynamically
			} else {
				System.err.println("Current level class name is not set.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to restart the game.");
		}
	}

	/**
	 * Pauses the game by stopping the game timeline and setting the paused flag.
	 */
	public void pauseGame() {
		if (!isPaused) {
			System.out.println("Pausing game...");
			timeline.stop();
			if (timeline != null) timeline.pause();
			isPaused = true;
		}
	}

	/**
	 * Resumes the game by restarting the game timeline and resetting the paused flag.
	 */
	public void resumeGame() {
		if (isPaused) {
			System.out.println("Resuming game...");
			timeline.play();
			if (timeline != null) timeline.play();
			isPaused = false;
		}
	}

	/**
	 * Exits the game and closes the application.
	 */
	private void exitGame() {
		System.out.println("Exiting game...");
		Platform.exit();  // This will close the application
	}

	/**
	 * Retrieves the user plane.
	 *
	 * @return The current user plane object.
	 */
	protected UserPlane getUser() {
		return user;
	}

	/**
	 * Retrieves the root group of the game scene.
	 *
	 * @return The root group of the game scene.
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Retrieves the current number of enemies in the level.
	 *
	 * @return The current number of enemies.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds a new enemy unit to the game.
	 *
	 * @param enemy The enemy unit to add.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Retrieves the maximum Y position of the enemies.
	 *
	 * @return The maximum Y position of the enemies.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Retrieves the screen width.
	 *
	 * @return The width of the screen.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the user has been destroyed.
	 *
	 * @return true if the user is destroyed, false otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the number of enemies in the level.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	private boolean shieldUsed = false; // Tracks if the shield power-up was used
	private boolean oneHitKillUsed = false; // Tracks if the one-hit-kill power-up was used
	private boolean powerUpUsed = false; // Tracks if any power-up has been used in the current level

	/**
	 * Binds the feature keys (such as activating power-ups) to the user plane and scene.
	 *
	 * @param scene The scene to which the keys are bound.
	 * @param userPlane The user plane object that will react to the key events.
	 */
	public void bindFeatureKeys(Scene scene, UserPlane userPlane) {
		Label timerLabel = new Label();
		timerLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-background-color: black; -fx-padding: 5px;");

		// Position the timer slightly above the bottom-left corner
		timerLabel.setLayoutX(10); // Left padding of 10 pixels
		timerLabel.setLayoutY(Main.getScreenHeight() - 80); // 80 pixels above the bottom
		getRoot().getChildren().add(timerLabel);

		scene.setOnKeyPressed(event -> {
			// Check if power-up has already been used
			if (powerUpUsed) {
				// If a power-up has been used, display the message only once when '1' or '2' is pressed
				if (event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.DIGIT2) {
					displayPowerUpMessage("Power-ups Used for This Level!");
					powerUpUsed = false; // Prevent the message from being displayed again
				}
				return; // Exit the method if power-up has been used
			}

			switch (event.getCode()) {
				case DIGIT1: // Key '1' activates shield
					if (!shieldUsed) {
						shieldUsed = true; // Mark shield as used
						userPlane.activateShield();
						displayPowerUpMessage("Shield Activated!");
						userPlane.showShield();  // Display the plane's shield effects
						System.out.println("Shield is activated...");

						// Show the UserShield image
						userShield.showShield();

						// Display the timer for the shield
						startShieldTimer(userPlane, timerLabel);

						// Mark that a power-up has been used
						powerUpUsed = true;
					}
					break;

				case DIGIT2: // Key '2' activates one-hit-kill
					if (!oneHitKillUsed) {
						oneHitKillUsed = true; // Mark one-hit-kill as used
						userPlane.activateOneHitKill();
						displayPowerUpMessage("Fire Boost Activated");
						userPlane.showOneHitKillEffect(); // Show visual effect for the one-hit-kill
						System.out.println("One-hit kill is activated...");

						// Display one-hit-kill timer and message
						startOneHitKillTimer(userPlane, timerLabel);

						// Mark that a power-up has been used
						powerUpUsed = true;
					}
					break;

				default:
					// No action for other keys
					break;
			}
		});
	}

	/**
	 * Resets the flags that track the usage of power-ups at the start of each level.
	 */
	public void resetPowerUpFlag() {
		powerUpUsed = false;
		shieldUsed = false; // Reset shield usage
		oneHitKillUsed = false; // Reset one-hit-kill usage
	}

	/**
	 * Starts the timer for the shield power-up, which lasts for 30 seconds.
	 *
	 * @param userPlane The user plane object to which the shield power-up is applied.
	 * @param timerLabel The label that displays the remaining time for the shield power-up.
	 */
	private void startShieldTimer(UserPlane userPlane, Label timerLabel) {
		final int[] timeLeft = {30}; // Timer starts at 30 seconds

		userPlane.showShield(); // Show shield visual effect
		userShield.showShield(); // Show the UserShield image

		Timeline shieldTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			if (timeLeft[0] > 0) {
				timerLabel.setText("Shield Active: " + timeLeft[0] + "s");
				timeLeft[0]--;
			} else {
				System.out.println("Shield deactivated...");
				timerLabel.setText(""); // Clear the timer display
				getRoot().getChildren().remove(timerLabel);

				// Hide the shield image after the timer runs out
				userShield.hideShield();
			}
		}));

		shieldTimer.setCycleCount(31); // Run 31 times (30 seconds countdown + 1 final frame)
		shieldTimer.play();
	}

	/**
	 * Starts the timer for the one-hit-kill power-up, which lasts for 10 seconds.
	 *
	 * @param userPlane The user plane object to which the one-hit-kill power-up is applied.
	 * @param timerLabel The label that displays the remaining time for the one-hit-kill power-up.
	 */
	private void startOneHitKillTimer(UserPlane userPlane, Label timerLabel) {
		final int[] timeLeft = {10}; // Timer starts at 10 seconds

		Timeline oneHitKillTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			if (timeLeft[0] > 0) {
				timerLabel.setText("One-hit Kill Active: " + timeLeft[0] + "s");
				timeLeft[0]--;
			} else {
				userPlane.hideOneHitKillEffect(); // Hide visual effect for one-hit-kill
				System.out.println("One-hit kill deactivated...");
				timerLabel.setText(""); // Clear the timer display
				getRoot().getChildren().remove(timerLabel);
			}
		}));

		oneHitKillTimer.setCycleCount(11); // Run 11 times (10 seconds countdown + 1 final frame)
		oneHitKillTimer.play();
	}

	/**
	 * Displays a message for the user, such as when a power-up is activated.
	 *
	 * @param message The message to display.
	 */
	public void displayPowerUpMessage(String message) {
		// Create the message label
		Label messageLabel = new Label(message);
		messageLabel.setStyle("-fx-font-family: 'Arial'; " +
				"-fx-font-size: 24px; " +
				"-fx-text-fill: #FFD700; " + // Gold text color
				"-fx-background-color: rgba(0, 0, 0, 0.7); " + // Semi-transparent background
				"-fx-padding: 10px; " +
				"-fx-border-radius: 5px; " +
				"-fx-background-radius: 5px; " +
				"-fx-alignment: center;");

		messageLabel.setTextAlignment(TextAlignment.CENTER);

		// Add label to the root group first to compute its size
		root.getChildren().add(messageLabel);

		// Use layoutBounds to get the width and height after the label is laid out
		messageLabel.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
			// Center the message label horizontally
			double labelWidth = newValue.getWidth();
			double labelHeight = newValue.getHeight();
			double centerX = (Main.getScreenWidth() - labelWidth) / 2;

			// Move the label a bit towards the left (adjust by subtracting a small value from centerX)
			double offsetX = 20; // Adjust this value to control how much to move left
			messageLabel.setLayoutX(centerX - offsetX); // Shift horizontally to the left

			// Position it in the upper-center part of the screen (adjust Y to be above center)
			double topMargin = 50; // You can adjust this value to control the vertical position
			messageLabel.setLayoutY(topMargin); // Set a fixed distance from the top
		});

		// Animation: Fade in, hold for 3 seconds, fade out
		FadeTransition fadeIn = new FadeTransition(Duration.millis(500), messageLabel);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);

		PauseTransition hold = new PauseTransition(Duration.seconds(3)); // Hold for 3 seconds

		FadeTransition fadeOut = new FadeTransition(Duration.millis(500), messageLabel);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);

		// Remove the label after fading out
		fadeOut.setOnFinished(event -> root.getChildren().remove(messageLabel));

		// Combine animations
		SequentialTransition sequentialTransition = new SequentialTransition(fadeIn, hold, fadeOut);
		sequentialTransition.play();
	}

	/**
	 * Sets the path for the collision sound.
	 *
	 * @param soundFilePath The path to the collision sound file.
	 */
	protected void setCollisionSound(String soundFilePath) {
		this.collisionSoundFile = soundFilePath;
	}

	/**
	 * Initializes the level. This method should be overridden by subclasses to set up the specific level.
	 */
	protected abstract void initializeLevel();{
	}
}

