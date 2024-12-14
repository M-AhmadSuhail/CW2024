package com.example.demo.Actor;

import com.example.demo.controller.Main;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Test class for ActiveActorDestructible.
 */
class ActiveActorDestructibleTest {

    private ActiveActorDestructible actor;

    /**
     * Concrete implementation of ActiveActorDestructible for testing purposes.
     */
    private static class TestActorDestructible extends ActiveActorDestructible {

        public TestActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);

            // Mock image resource to bypass loading actual files during tests
            this.setImage(new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/w8AAwEB/CFqVxEAAAAASUVORK5CYII="));
        }

        @Override
        public void updateActor() {
            // Custom behavior for testing, if needed
        }

        @Override
        public void takeDamage() {
            // Simulate taking damage
            destroy();
        }
    }

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        // Initialize a test actor instance
        actor = new TestActorDestructible("enemyplane.png", 100, 0, 0);
    }

    @Test
    void testIsOutOfBounds() {
        double screenWidth = 500;

        // Test within bounds
        assertFalse(actor.isOutOfBounds(250, screenWidth), "Actor should be within bounds.");

        // Test out of bounds (left)
        assertTrue(actor.isOutOfBounds(-10, screenWidth), "Actor should be out of bounds on the left.");

        // Test out of bounds (right)
        assertTrue(actor.isOutOfBounds(510, screenWidth), "Actor should be out of bounds on the right.");
    }

    @Test
    void testDestroy() {
        assertFalse(actor.isDestroyed(), "Actor should not be destroyed initially.");

        actor.destroy();

        assertTrue(actor.isDestroyed(), "Actor should be marked as destroyed after calling destroy.");
    }

    @Test
    void testTakeDamage() {
        assertFalse(actor.isDestroyed(), "Actor should not be destroyed initially.");

        actor.takeDamage();

        assertTrue(actor.isDestroyed(), "Actor should be destroyed after taking damage.");
    }
}
