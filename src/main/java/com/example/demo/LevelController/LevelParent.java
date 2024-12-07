package com.example.demo.LevelController;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Plane.FighterPlane;
import com.example.demo.Plane.UserPlane;
import com.example.demo.Plane.UserPlane2;
import com.example.demo.UI.EndGameScreen;
import com.example.demo.UI.KillDisplay;
import com.example.demo.controller.Controller;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;



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
	protected final LevelView levelView;
	private final KillDisplay killDisplay;
	protected abstract LevelView instantiateLevelView();

	// Pause state
	protected boolean isPaused = false;
	private boolean isCooldown = false; // Cooldown state

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
		this.currentNumberOfEnemies = 0;
		this.MESSAGE_ON_SCREEN = messageOnScreen;  // Set the message here
		this.killDisplay = new KillDisplay(10, 70); // Adjust position as needed
		root.getChildren().add(killDisplay.getContainer());
		initializeTimeline();
		friendlyUnits.add(user);
	}
	public static void setController(Controller gameController) {
		controller = gameController;
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();


	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showKillDisplay();
		levelView.entryMessage(MESSAGE_ON_SCREEN);
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String nextLevelClassName) {
		timeline.stop();
		setChanged();
		notifyObservers(nextLevelClassName);
	}

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();

		boolean planeCollision = handlePlaneCollisions();
		boolean userProjectileCollision = handleUserProjectileCollisions();
		boolean enemyProjectileCollision = handleEnemyProjectileCollisions();

		removeAllDestroyedActors();
		updateHitCount(userProjectileCollision);
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setPreserveRatio(false);
		background.setSmooth(true);

		background.setOnKeyPressed(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP) getUser().moveUp();
			if (kc == KeyCode.DOWN) getUser().moveDown();
			if (kc == KeyCode.SPACE) fireProjectile();
			if (kc == KeyCode.P) togglePause();  // Pause/unpause the game
		});

		background.setOnKeyReleased(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP || kc == KeyCode.DOWN) getUser().stop();
		});

		root.getChildren().add(background);
	}
	private void togglePause() {
		if (isPaused) {
			resumeGame();
		} else {
			pauseGame();
		}
	}
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

	private void startCooldown() {
		isCooldown = true; // Set cooldown flag to true
		PauseTransition cooldownTimer = new PauseTransition(Duration.millis(PROJECTILE_COOLDOWN));
		cooldownTimer.setOnFinished(event -> isCooldown = false); // Reset the cooldown after the specified time
		cooldownTimer.play();
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors); // Remove from scene
		actors.removeAll(destroyedActors);            // Remove from list
	}

	private boolean handlePlaneCollisions() {
		return handleCollisions(friendlyUnits, enemyUnits);
	}

	private boolean handleUserProjectileCollisions() {
		return handleCollisions(userProjectiles, enemyUnits);
	}

	private boolean handleEnemyProjectileCollisions() {
		return handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private boolean handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
		boolean collisionOccurred = false;
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
					collisionOccurred = true;
				}
			}
		}
		return collisionOccurred;
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
		levelView.updateKills(user.getNumberOfHits());
	}

	private void updateHitCount(boolean collisionDetected) {
		if (collisionDetected) {
			user.incrementHitCount(); // Update hit count if a collision occurred
			System.out.println("User hit-count: " + user.getNumberOfHits());
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
		EndGameScreen endGameMenu = new EndGameScreen("You Win!", screenWidth, screenHeight);
		attachEndGameMenu(endGameMenu);
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
		EndGameScreen endGameMenu = new EndGameScreen("Oops! Try Again?", screenWidth, screenHeight);
		attachEndGameMenu(endGameMenu);
	}
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
	private void restartGame () {
		try {
			controller.goToLevel(Controller.LEVEL_ONE_CLASS_NAME); // Call Controller to restart the game
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to restart the game.");
		}
	}

	public void pauseGame () {
		if (!isPaused) {
			System.out.println("Pausing game...");
			timeline.stop();
			if (timeline != null) timeline.pause();
			// Add more elements to pause
			isPaused = true;
		}
	}

	public void resumeGame () {
		if (isPaused) {
			System.out.println("Resuming game...");
			timeline.play();
			if (timeline != null) timeline.play();
			// Add more elements to resume
			isPaused = false;
		}
	}

	protected UserPlane getUser () {
		return user;
	}

	protected Group getRoot () {
		return root;
	}

	protected int getCurrentNumberOfEnemies () {
		return enemyUnits.size();
	}

	protected void addEnemyUnit (ActiveActorDestructible enemy){
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition () {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth () {
		return screenWidth;
	}

	protected boolean userIsDestroyed () {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies () {
		currentNumberOfEnemies = enemyUnits.size();
	}

}
