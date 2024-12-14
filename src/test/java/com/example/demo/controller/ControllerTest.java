package com.example.demo.controller;

import com.example.demo.LevelController.LevelParent;
import com.example.demo.LevelController.LevelView;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private Stage stage;
    private LevelView levelView;
    private Controller controller;
    private Timeline timeline;

    // Create a minimal subclass for testing and implement the missing methods
    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit once for all tests
        Platform.startup(() -> {});
    }
    @BeforeEach
    public void setUp() {
        // Ensure the JavaFX runtime is initialized correctly by running in the JavaFX application thread
        Platform.runLater(() -> {
            stage = new Stage();
            levelView = new LevelView(new javafx.scene.Group(), 800, 600);
            timeline = new Timeline(); // Empty timeline, just to ensure no null pointer

            // Initialize the controller with actual instances
            controller = new Controller(stage, levelView);
            controller.timeline = timeline; // Inject the actual timeline into the controller
        });
    }

    @Test
    public void testLaunchGame() {
        // Run the launch game method in the JavaFX application thread
        Platform.runLater(() -> {
            try {
                // Launch the game and verify that the stage shows
                controller.launchGame();

                // Since we can't easily check if the scene was set (depends on your environment),
                // we check if the stage is showing
                assertTrue(stage.isShowing(), "Stage should be visible after launching the game");
            } catch (Exception e) {
                fail("Exception should not be thrown during game launch");
            }
        });
    }

    @Test
    public void testGoToLevel() {
        Platform.runLater(() -> {
            try {
                // Use the TestLevelParent subclass for the test
                controller.goToLevel("com.example.demo.controller.ControllerTest$TestLevelParent");

                // Verify that the level was initialized and the scene was set on the stage
                assertNotNull(stage.getScene(), "Scene should be set after changing level");
            } catch (Exception e) {
                fail("Exception should not be thrown during level transition");
            }
        });
    }

    @Test
    public void testPauseGame() {
        // Run the pause game method on the JavaFX application thread
        Platform.runLater(() -> {
            controller.pauseGame();

            // Verify that the timeline is paused
            assertTrue(timeline.getStatus() == Timeline.Status.PAUSED, "Timeline should be paused");

            // Verify that the isPaused flag is set to true
            assertTrue(controller.isPaused, "Game should be paused");
        });
    }

    @Test
    public void testResumeGame() {
        // Run the resume game method on the JavaFX application thread
        Platform.runLater(() -> {
            controller.resumeGame();

            // Verify that the timeline is playing
            assertTrue(timeline.getStatus() == Timeline.Status.RUNNING, "Timeline should be playing");

            // Verify that the isPaused flag is set to false
            assertFalse(controller.isPaused, "Game should be resumed");
        });
    }

    @Test
    public void testRestartGame() {
        // Run the restart game method on the JavaFX application thread
        Platform.runLater(() -> {
            controller.restartGame();

            // Verify that the timeline is playing again
            assertTrue(timeline.getStatus() == Timeline.Status.RUNNING, "Timeline should be running after restart");

            // Verify that the game is relaunched
            assertTrue(stage.isShowing(), "Stage should be visible after restarting the game");
        });
    }

    @Test
    public void testCloseGame() {
        // Simulate closing the game on the JavaFX application thread
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.closeGame(), "Closing the game should not throw an exception");
        });
    }


}
