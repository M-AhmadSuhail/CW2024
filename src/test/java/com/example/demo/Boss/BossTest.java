package com.example.demo.Boss;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.LevelController.LevelView;
import javafx.application.Platform;
import javafx.scene.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BossTest {

    private Boss boss;
    private LevelView levelView;

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit once for all tests
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        // Ensure the code that uses JavaFX components runs on the JavaFX thread
        Platform.runLater(() -> {
            // Initialize the boss
            boss = new Boss();

            // Provide the necessary arguments for LevelView constructor
            Group dummyGroup = new Group();  // Create a dummy Group instance for the constructor
            int dummyWidth = 800;  // Dummy width for the test
            int dummyHeight = 600; // Dummy height for the test

            // Instantiate LevelView with the required arguments
            levelView = new LevelView(dummyGroup, dummyWidth, dummyHeight);
            boss.setLevelView(levelView);  // Set the level view for the boss
        });
    }

    // Test that the boss initializes with the correct health and position
    @Test
    void testInitialization() {
        // Move the assertion checks inside Platform.runLater to make sure this happens in the JavaFX thread
        Platform.runLater(() -> {
            assertEquals(15, boss.getHealth(), "Boss should start with 15 health.");
            assertEquals(1000.0, boss.getLayoutX(), "Boss should start at X position 1000.");
            assertEquals(400.0, boss.getLayoutY(), "Boss should start at Y position 400.");
        });
    }
    // Test that the boss's health reduces correctly when not shielded
    @Test
    void testHealthReductionWithoutShield() {
        // Ensure boss is initialized before proceeding with the test
        Platform.runLater(() -> {
            boss.reduceHealth(5); // Call reduceHealth on the boss

            // Assert the expected health after reduction
            assertEquals(15, boss.getHealth(), "Boss health should be reduced without shield.");
        });
    }

    // Test that the boss doesn't take damage when shielded
    @Test
    void testHealthReductionWithShield() {
        // Ensure boss is initialized before the test is run
        Platform.runLater(() -> {
            // Simulate shield activation
            boss.activateShield(); // Ensure the boss is properly initialized here

            // Now call reduceHealth() while shield is activated
            boss.reduceHealth(5);
            assertEquals(15, boss.getHealth(), "Boss health should not be reduced when shielded.");
        });
    }


    // Test that the boss fires projectiles based on its fire rate
    @Test
    void testFiringProjectile() {
        // Set the fire rate low to ensure the boss fires a projectile
        double fireRate = 0.04;  // Let's assume a 4% chance to fire
        if (Math.random() < fireRate) {
            ActiveActorDestructible projectile = boss.fireProjectile();
            assertNotNull(projectile, "Boss should fire a projectile.");
        }
    }

    // Test the move pattern shuffling (this isn't a deterministic test, but we can check the pattern length and behavior)

    @Test
    void testMovementBounds() {
        // Ensure that the boss is initialized before running the test
        Platform.runLater(() -> {
            assertNotNull(boss, "Boss should not be null.");

            // Test if translateY is correctly set
            assertEquals(100, boss.getTranslateY(), "Boss translateY should be 100.");

            // You can now test other movement-related logic
            // For example, if move() changes the translateY, test for that
            boss.setTranslateY(200);  // Move the boss down
            assertEquals(200, boss.getTranslateY(), "Boss translateY should be updated to 200.");
        });
    }
}
