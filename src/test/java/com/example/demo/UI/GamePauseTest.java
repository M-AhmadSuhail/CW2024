package com.example.demo.UI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GamePause functionality, focusing on show and showPausePopup methods.
 */
public class GamePauseTest {

    private static Stage primaryStage;
    private GamePause gamePause;

    /**
     * Initialize JavaFX runtime once before all tests.
     */
    @BeforeAll
    public static void initJFX() {
        new Thread(() -> Application.launch(DummyApp.class)).start();
        while (DummyApp.primaryStage == null) {
            try {
                Thread.sleep(100); // Wait for JavaFX application to start
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        primaryStage = DummyApp.primaryStage;
    }

    @BeforeEach
    public void setUp() {
        // Initialize GamePause with dummy actions
        gamePause = new GamePause(() -> {}, () -> {}, () -> {});
    }

    @Test
    public void testShow() {
        Platform.runLater(() -> {
            // Call the show method
            gamePause.show();

            // Verify that the pause stage is created
            assertNotNull(gamePause.pauseStage, "Pause stage should not be null");
            assertTrue(gamePause.pauseStage.isShowing(), "Pause stage should be showing");

            // Close the stage after verification
            gamePause.pauseStage.close();
        });

        waitForRunLater();
    }

    @Test
    public void testShowPausePopup() {
        Platform.runLater(() -> {
            // Call the showPausePopup method
            gamePause.showPausePopup();

            // Verify that the pause stage is created and contains buttons
            assertNotNull(gamePause.pauseStage, "Pause stage should not be null");

            VBox layout = (VBox) gamePause.pauseStage.getScene().getRoot();
            assertEquals(3, layout.getChildren().stream().filter(node -> node instanceof Button).count(),
                    "Pause menu should have 3 buttons");

            // Close the stage after verification
            gamePause.pauseStage.close();
        });

        waitForRunLater();
    }

    /**
     * Utility method to wait for JavaFX Platform.runLater() tasks to complete.
     */
    private void waitForRunLater() {
        try {
            Thread.sleep(500); // Adjust time if needed for UI thread to finish tasks
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A dummy JavaFX application for testing purposes.
     */
    public static class DummyApp extends Application {
        private static Stage primaryStage;

        @Override
        public void start(Stage stage) {
            primaryStage = stage;
            primaryStage.setScene(new Scene(new VBox(), 800, 600));
            primaryStage.show();
        }
    }
}
