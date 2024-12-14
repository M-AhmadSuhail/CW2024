package com.example.demo.controller;

import com.example.demo.controller.Main;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private Main mainApp;

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit once for all tests
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        mainApp = new Main();
    }
    @Test
    void testStartMethod() throws Exception {
        // Launch the application on the JavaFX thread.
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage(); // Create a new Stage for testing
                mainApp.start(stage); // Call the start method

                // Check that the stage has the correct title and properties
                assertEquals("Sky Battle - Main Menu", stage.getTitle());  // Updated title
                assertEquals(1300.0, stage.getWidth());
                assertEquals(750.0, stage.getHeight());
                assertFalse(stage.isResizable());

                // Check that the scene has been set correctly, meaning the main menu was shown
                Scene scene = stage.getScene();
                assertNotNull(scene);  // Scene should not be null, meaning the main menu is set
            } catch (Exception e) {
                fail("An error occurred while testing the start method: " + e.getMessage());
            }
        });

        // Sleep a bit to allow JavaFX to initialize
        Thread.sleep(1000); // Ensure we give the UI time to launch and process
    }

    @Test
    void testScreenDimensions() {
        // Test the screen width and height
        assertEquals(1300, Main.getScreenWidth());
        assertEquals(750, Main.getScreenHeight());
    }
}
