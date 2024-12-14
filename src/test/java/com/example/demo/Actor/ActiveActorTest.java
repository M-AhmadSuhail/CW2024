//package com.example.demo.Actor;
//
//import javafx.application.Platform;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Test class for ActiveActor methods.
// */
//class ActiveActorTest {
//
//    private ActiveActor actor;
//
//    /**
//     * A concrete implementation of ActiveActor for testing purposes.
//     */
//    private static class TestActor extends ActiveActor {
//
//        public TestActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
//            super(imageName, imageHeight, initialXPos, initialYPos);
//        }
//
//        @Override
//        public void updatePosition() {
//            moveHorizontally(15);
//            moveVertically(10);
//        }
//    }
//
//    @BeforeEach
//    void setUp() {
//        Platform.startup(() -> {}); // Initialize the JavaFX toolkit for testing.
//        actor = new TestActor("test-image.png", 100, 0, 0);
//    }
//
//    @Test
//    void testMoveHorizontally() {
//        double initialX = actor.getTranslateX();
//        actor.moveHorizontally(20);
//        assertEquals(initialX + 20, actor.getTranslateX(), "Actor did not move correctly horizontally.");
//    }
//
//
//
//    @Test
//    void testMoveVertically() {
//        double initialY = actor.getTranslateY();
//        actor.moveVertically(25);
//        assertEquals(initialY + 25, actor.getTranslateY(), "Actor did not move correctly vertically.");
//    }
//
//    @Test
//    void testUpdatePosition() {
//        double initialX = actor.getTranslateX();
//        double initialY = actor.getTranslateY();
//        actor.updatePosition();
//        assertEquals(initialX + 15, actor.getTranslateX(), "Actor did not update position correctly horizontally.");
//        assertEquals(initialY + 10, actor.getTranslateY(), "Actor did not update position correctly vertically.");
//    }
//}


package com.example.demo.Actor;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiveActorTest {

    private ActiveActor actor;

    // Concrete implementation of ActiveActor for testing purposes
    private static class TestActor extends ActiveActor {

        public TestActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
            super(imageName, imageHeight, initialXPos, initialYPos);
        }

        @Override
        public void updatePosition() {
            moveHorizontally(15);
            moveVertically(10);
        }
    }

    @BeforeAll
    static void initToolkit() {
        // Initialize JavaFX toolkit once for all tests
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        // Create a new instance of TestActor
        actor = new TestActor("enemyplane.png", 100, 0, 0);
    }

    @Test
    void testMoveHorizontally() {
        double initialX = actor.getTranslateX();
        actor.moveHorizontally(20);
        assertEquals(initialX + 20, actor.getTranslateX(), "Actor did not move correctly horizontally.");
    }

    @Test
    void testMoveVertically() {
        double initialY = actor.getTranslateY();
        actor.moveVertically(25);
        assertEquals(initialY + 25, actor.getTranslateY(), "Actor did not move correctly vertically.");
    }

    @Test
    void testUpdatePosition() {
        double initialX = actor.getTranslateX();
        double initialY = actor.getTranslateY();
        actor.updatePosition();
        assertEquals(initialX + 15, actor.getTranslateX(), "Actor did not update position correctly horizontally.");
        assertEquals(initialY + 10, actor.getTranslateY(), "Actor did not update position correctly vertically.");
    }
}
