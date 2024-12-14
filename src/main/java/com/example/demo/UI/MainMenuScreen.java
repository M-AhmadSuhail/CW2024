    package com.example.demo.UI;

import com.example.demo.LevelController.LevelView;
import com.example.demo.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;

/**
 * The MainMenuScreen class is responsible for displaying the main menu of the game.
 * It provides options to start the game, view instructions on how to play, or quit the game.
 * The menu screen includes a custom background image, a game title, and styled buttons.
 */
public class MainMenuScreen {

    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/MenuBG.jpg"; // Path to background image
    private static final String CUSTOM_FONT = "/com/example/demo/Fonts/PixelifySans-VariableFont_wght.ttf"; // Path to custom font

    /**
     * Starts the main menu screen of the game, where the user can start the game, view instructions, or quit.
     *
     * @param primaryStage The primary stage for the application.
     */
    public void start(Stage primaryStage) {
        // Load the custom font for the game title and buttons
        Font customFont = Font.loadFont(getClass().getResourceAsStream(CUSTOM_FONT), 48);
        if (customFont == null) {
            System.err.println("Error: Unable to load custom font at path: " + CUSTOM_FONT);
        }

        // Load the background image for the main menu screen
        ImageView background = null;
        try {
            background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE).toExternalForm()));
            background.setFitWidth(1300); // Set width of the background image
            background.setFitHeight(750); // Set height of the background image
            background.setPreserveRatio(false); // Allow the image to stretch
        } catch (NullPointerException e) {
            System.err.println("Error: Unable to load background image at path: " + BACKGROUND_IMAGE);
            e.printStackTrace();
        }

        // Create and style the game title text
        Text gameTitle = new Text("Sky Battle!");
        gameTitle.setFont(customFont != null ? customFont : Font.font(48)); // Use custom font or fallback
        gameTitle.setStyle(
                "-fx-fill: #ff007f;" + // Pink text color
                        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 5);" // Drop shadow effect
        );
        gameTitle.setTranslateY(-100); // Position the title slightly above the center

        // Create buttons for Play, How to Play, and Quit
        Button playButton = createButton("Play", event -> {
            LevelView levelView = new LevelView(new javafx.scene.Group(), 3, 0);
            Controller controller = new Controller(primaryStage, levelView);
            controller.launchGame(); // Launch the game when Play button is clicked
        });

        Button howToPlayButton = createButton("How to Play", event -> showHowToPlay()); // Show the instructions when clicked
        Button quitButton = createButton("Quit", event -> primaryStage.close()); // Close the application when Quit button is clicked

        // Layout the buttons vertically with spacing
        VBox buttonsLayout = new VBox(20);
        buttonsLayout.getChildren().addAll(gameTitle, playButton, howToPlayButton, quitButton);
        buttonsLayout.setAlignment(Pos.CENTER); // Center the buttons
        buttonsLayout.setTranslateY(50); // Adjust vertical position of buttons

        // Create the root layout and add background and buttons
        StackPane root = new StackPane();
        if (background != null) {
            root.getChildren().add(background); // Add the background image to the root layout
        }
        root.getChildren().add(buttonsLayout); // Add the buttons to the root layout

        // Set the scene with the root layout and show the stage
        Scene scene = new Scene(root, 1300, 750);
        primaryStage.setTitle("Sky Battle - Main Menu"); // Set the window title
        primaryStage.setScene(scene); // Set the scene to the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * Creates a styled button with a specified text and event handler.
     *
     * @param text The text to display on the button.
     * @param eventHandler The action to perform when the button is clicked.
     * @return The created button.
     */
    private Button createButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setPrefWidth(250); // Set the preferred width of the button
        button.setPrefHeight(60); // Set the preferred height of the button
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff7f50, #ff6347);" +  // Gradient background
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-family: 'Press Start 2P';" + // Retro font for button text
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 10, 0, 0, 5);" // Drop shadow effect
        );
        button.setCursor(Cursor.HAND); // Change cursor to hand when hovering over the button
        button.setOnAction(eventHandler); // Set the event handler for button click actions

        // Hover effect to change the button appearance when mouse is over it
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #ffa07a, #ff4500);" +  // Lighter gradient on hover
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 20px;" +
                            "-fx-border-radius: 15;" +
                            "-fx-background-radius: 15;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 15, 0, 0, 5);" // Stronger shadow
            );
            button.setScaleX(1.05); // Slightly scale the button on hover
            button.setScaleY(1.05);
        });
        // Reset the button appearance when the mouse exits
        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #ff7f50, #ff6347);" +  // Default gradient
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 20px;" +
                            "-fx-border-radius: 15;" +
                            "-fx-background-radius: 15;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 10, 0, 0, 5);" // Default shadow
            );
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
        return button; // Return the created button
    }

    /**
     * Opens a modal window displaying the instructions on how to play the game.
     */
    private void showHowToPlay() {
        Stage howToPlayStage = new Stage();
        howToPlayStage.initModality(Modality.APPLICATION_MODAL); // Make this a modal window
        howToPlayStage.setTitle("How to Play"); // Set the title of the instructions window

        // Create and style the "How to Play" title
        Text gameTitle = new Text("How to Play");
        gameTitle.setFont(Font.font("Press Start 2P", 28)); // Retro pixel font
        gameTitle.setStyle("-fx-fill: #ff007f;"); // Pink color for the title

        // Create a GridPane for displaying control instructions
        GridPane controlsGrid = new GridPane();
        controlsGrid.setHgap(30); // Horizontal gap between columns
        controlsGrid.setVgap(10); // Vertical gap between rows
        controlsGrid.setAlignment(Pos.CENTER); // Center the grid

        // Control actions and their corresponding keys
        String[][] controls = {
                {"Move Up:", "↑"},
                {"Move Down:", "↓"},
                {"Move Left:", "←"},
                {"Move Right:", "→"},
                {"Shoot:", "SPACE"},
                {"Pause:", "P"},
                {"Restart:", "R"},
                {"Power-Ups:", "Kill enemy planes"} // Additional power-up instructions
        };

        // Add control instructions to the grid
        for (int i = 0; i < controls.length; i++) {
            Text action = new Text(controls[i][0]);
            action.setFont(Font.font("Press Start 2P", 18));
            action.setStyle("-fx-fill: yellow;"); // Yellow text for actions

            Text key = new Text(controls[i][1]);
            key.setFont(Font.font("Press Start 2P", 18));
            key.setStyle("-fx-fill: white;"); // White text for keys

            controlsGrid.add(action, 0, i); // Add action label to column 0
            controlsGrid.add(key, 1, i);   // Add key to column 1
        }

        // Close instructions text
        Text closeInstruction = new Text("Press ESC to close");
        closeInstruction.setFont(Font.font("Press Start 2P", 14));
        closeInstruction.setStyle("-fx-fill: yellow;");

        // Back button to close the instruction window
        Button backButton = new Button("Back");
        backButton.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-family: 'Press Start 2P';" +
                        "-fx-border-color: white; -fx-border-width: 2;"
        );
        backButton.setOnAction(e -> howToPlayStage.close()); // Close the instructions window

        // Layout the controls and buttons vertically
        VBox layout = new VBox(20, gameTitle, controlsGrid, closeInstruction, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: black; -fx-padding: 20;");

        // Create a scene and set it to the how-to-play window
        Scene howToPlayScene = new Scene(layout, 600, 400);

        // Handle ESC key to close the window
        howToPlayScene.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                howToPlayStage.close();
            }
        });

        // Set and show the how-to-play stage
        howToPlayStage.setScene(howToPlayScene);
        howToPlayStage.showAndWait(); // Wait for the window to close before returning
    }
}
