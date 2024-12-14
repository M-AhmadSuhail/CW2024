package com.example.demo.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EndGameScreenTest {

    private EndGameScreen endGameScreen;

    // Ensure that JavaFX is initialized only once before all tests
    @BeforeAll
    public static void setUpBeforeAll() {
        // Launch JavaFX Application once for all tests
        Application.launch(TestApp.class);
    }

    @BeforeEach
    public void setUp() {
        // Initialize EndGameScreen for each test
        endGameScreen = new EndGameScreen("Game Over", 800, 600);
    }

    @Test
    public void testMessageVisibility() {
        // Test if the message is visible and the components are correctly added
        assertNotNull(endGameScreen.getChildren(), "EndGameScreen should contain components.");
        assertTrue(endGameScreen.getChildren().size() > 0, "EndGameScreen should contain at least one child.");
        assertTrue(endGameScreen.getChildren().get(0) instanceof javafx.scene.text.Text, "First child should be a Text component.");
    }

    @Test
    public void testButtonCreation() {
        // Verify that both buttons are created
        Button restartButton = endGameScreen.getRestartButton();
        Button exitButton = endGameScreen.getExitButton();

        assertNotNull(restartButton, "Restart button should be created.");
        assertNotNull(exitButton, "Exit button should be created.");
        assertEquals("Restart", restartButton.getText(), "Restart button text should be 'Restart'.");
        assertEquals("Exit", exitButton.getText(), "Exit button text should be 'Exit'.");
    }

    @Test
    public void testButtonDimensions() {
        // Verify that the buttons have the correct preferred size
        Button restartButton = endGameScreen.getRestartButton();
        Button exitButton = endGameScreen.getExitButton();

        assertEquals(200, restartButton.getPrefWidth(), "Restart button width should be 200.");
        assertEquals(60, restartButton.getPrefHeight(), "Restart button height should be 60.");

        assertEquals(200, exitButton.getPrefWidth(), "Exit button width should be 200.");
        assertEquals(60, exitButton.getPrefHeight(), "Exit button height should be 60.");
    }

    // Test Application class to initialize JavaFX toolkit
    public static class TestApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            // Create an empty scene to initialize the JavaFX toolkit
            primaryStage.setScene(new Scene(new VBox(), 1, 1));
            primaryStage.show();
        }
    }
}
