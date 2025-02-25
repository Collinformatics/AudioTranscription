package com.example.audiotranscription;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.geometry.Pos;



public class UI extends Application {
    // Define colors as strings
    private static final String red = "#FF0000";
    private static final String redLight = "#DE2828";
    private static final String redDark = "#560000";
    private static final String green = "#39FF14";
    private static final String greenDark= "#003000";
    private static final String grey = "#303030";
    private static final String greyDark = "#252525";
    private static final String black = "#202020";
    private static final String titleBarColor = "#171717";

    private double xOffset = 0;
    private double yOffset = 0;

    // Text parameters
    String buttonFont = "Verdana";
    int buttonFontSize = 22;

    // Program parameters
    private boolean isRecording = false; // Toggle state


    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED); // Remove OS title bar

        // ================================== Title Bar ==================================
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: " + titleBarColor + "; " +
                          "-fx-padding: 10px;");

        // Vertically center the content within the HBox
        titleBar.setAlignment(Pos.CENTER_LEFT); // Align items to the left, but center vertically

        // Application Title
        Label title = new Label("Audio Transcription");
        title.setStyle(String.format("-fx-text-fill: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx;",
                green, buttonFont, buttonFontSize-4));

        // Center the text vertically within the label
        title.setAlignment(Pos.CENTER); // Vertically center the label text
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Close button
        Button closeButton = new Button("X");
        closeButton.setStyle(String.format("-fx-background-color: %s; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px;", redLight));
        closeButton.setOnAction(e -> primaryStage.close());

        // Add title, spacer, and close button to the title bar
        titleBar.getChildren().addAll(title, spacer, closeButton);

        // Enable window dragging
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });


        // ================================ Record Button ================================
        Button button = new Button("Record");
        // Set button size
        int buttonWidth = 200;
        int buttonHeight = 60;
        button.setMinWidth(buttonWidth);
        button.setPrefWidth(buttonWidth);
        button.setMaxWidth(buttonWidth);
        button.setMinHeight(buttonHeight);
        button.setPrefHeight(buttonHeight);
        button.setMaxHeight(buttonHeight);

        // Default button style
        String defaultStyle = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx; ",
                black, green, green, buttonFont, buttonFontSize);

        // Button hover style
        String hoverStyle = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %dpx; ",
                greenDark, green, green, buttonFont, buttonFontSize);

        // Recording button style
        String recordingStyle = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx; ",
                redDark, red, red, buttonFont, buttonFontSize);

        button.setStyle(defaultStyle);
        button.setOnMouseEntered(e -> {
            button.setStyle(hoverStyle);  // Change background to greenDark when hovered
        });
        button.setOnMouseExited(e -> {
            button.setStyle(defaultStyle);  // Revert to default style when the mouse leaves
        });

        // Toggle color on click
        button.setOnAction(e -> {
            isRecording = !isRecording; // Toggle state
            if (isRecording) {
                button.setText("Stop Recording");
                button.setStyle(recordingStyle); // Change to red when recording
            } else {
                button.setText("Record");
                button.setStyle(defaultStyle); // Change back to green when stopped
            }
        });

        // Create an invisible focusable StackPane
        StackPane focusPane = new StackPane();
        focusPane.setStyle("-fx-background-color: transparent;"); // Make it invisible

        // Allow it to capture key events
        focusPane.setFocusTraversable(true);

        // Ensure focus is applied after UI is rendered
        Platform.runLater(() -> focusPane.requestFocus());


        // ================================= Main Layout =================================
        StackPane centerPane = new StackPane();
        centerPane.setStyle("-fx-background-color: " + greyDark + ";");
        centerPane.getChildren().add(button);

        // ======= Main Layout =======
        BorderPane root = new BorderPane();
        root.setTop(titleBar); // Add custom title bar
        root.setCenter(centerPane); // Empty content for now
        root.setStyle("-fx-background-color: " + grey + ";"); // Dark background

        // Create the scene
        Scene scene = new Scene(root, 600, 900);

        // Keyboard shortcuts
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close(); // Pressing ESC closes the window
            }
            else if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                button.fire(); // Pressing ENTER triggers the button

            }
        });
        // Ensure the scene gets focus after the UI is fully loaded
        Platform.runLater(() -> scene.getRoot().requestFocus());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
