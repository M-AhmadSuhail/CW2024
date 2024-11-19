    package com.example.demo;

    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Alert.AlertType;
    import javafx.stage.Stage;

    public class LevelTransitionManager {

        public void transitionToNextLevel(Stage stage, String nextLevelClassName, double screenWidth, double screenHeight) {
            try {
                System.out.println("LTM.j - Transitioning to next level: " + nextLevelClassName + screenWidth + screenHeight); // Debugging log

                // Dynamically load the class and create an instance of the next level
                Class<?> levelClass = Class.forName(nextLevelClassName);
                LevelParent nextLevel;

                // Use reflection to initialize the next level instance
                try {
                    // Try to instantiate using a constructor with screen dimensions
                    nextLevel = (LevelParent) levelClass.getConstructor(double.class, double.class)
                            .newInstance(screenWidth, screenHeight);
                } catch (NoSuchMethodException e) {
                    // Fallback to a default constructor if the specific one isn't found
                    nextLevel = (LevelParent) levelClass.getConstructor().newInstance();
                }

                // Initialize the scene and set it to the stage
                Scene scene = nextLevel.initializeScene(); // This method should handle the scene setup
                stage.setScene(scene);                    // Set the new Scene to the Stage
                scene.getRoot().requestFocus();           // Request focus on the root of the Scene
                nextLevel.startGame();                    // Start the next level

            } catch (Exception e) {
                // Handle any exceptions during the transition process
                System.out.println("Error transitioning to next level: " + e.getMessage());
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Error transitioning to next level: " + e.getMessage());
                alert.show();
            }
        }
    }
