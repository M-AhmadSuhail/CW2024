package com.example.demo.UI;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MainMenuScreen.
 */
public class MainMenuScreenTest {

    private MainMenuScreen mainMenuScreen;
    private Stage primaryStage;

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit once for all tests
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // Ensure that the JavaFX app thread is being used for UI creation
        Platform.runLater(() -> {
            try {
                mainMenuScreen = new MainMenuScreen();
                primaryStage = new Stage();
                mainMenuScreen.start(primaryStage);  // Start the main menu UI
            } catch (Exception e) {
                e.printStackTrace(); // Log any exceptions
            } finally {
                latch.countDown();  // Signal that the setup is complete
            }
        });

        // Wait until the setup is complete
        latch.await();  // May throw InterruptedException
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        // Ensure the stage is closed on the JavaFX thread
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            primaryStage.close();
            latch.countDown();  // Signal that the stage is closed
        });
        latch.await();  // May throw InterruptedException
    }

    @Test
    public void testTitleIsDisplayed() {
        // Mock the title text setup and ensure the title is displayed
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                assertTrue(primaryStage.getScene().getRoot().toString().contains("Sky Battle!"),
                        "Game title should be displayed on the main menu.");
            } finally {
                latch.countDown();
            }
        });

        try {
            latch.await();  // Wait until the UI thread completes
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle InterruptedException
        }
    }


}
