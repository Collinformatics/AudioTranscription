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
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.geometry.Insets;




public class UI extends Application {
    // Define colors as strings
    private static final String red = "#FF0000";
    private static final String redLight = "#DE2828";
    private static final String redMedium = "#8D0000";
    private static final String redDark = "#560000";
    private static final String green = "#39FF14";
    private static final String greenDark= "#003000";
    private static final String pink = "#EE00FF";
    private static final String grey = "#303030";
    private static final String greyDark = "#252525";
    private static final String black = "#202020";
    private static final String titleBarColor = "#171717";

    // Text parameters
    String buttonFont = "Verdana";
    int buttonFontSize = 22;

    // Program parameters
    private boolean isRecording = false;
    private double windowWidth = 600;
    private double windowHeight = Screen.getPrimary().getVisualBounds().getHeight();
    private double headerHeight = 40;
    private double spacer = 10;



    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED); // Remove OS title bar
        primaryStage.setResizable(true);  // Ensure the stage can be resized

        // ========================== Initialize Root Container ==========================
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + grey + ";"); // Window background
        root.setPadding(Insets.EMPTY);

        //
        Scene scene = new Scene(root, windowWidth, windowHeight);

        // ================================== Title Bar ==================================
        HBox titleBar = new HBox();
        titleBar.setStyle(String.format("-fx-background-color: %s; " +
                "-fx-padding: 0px;", titleBarColor));
        titleBar.setPrefHeight(headerHeight);

        // Application Title
        Label title = new Label("Audio Transcription");
        title.setStyle(String.format("-fx-text-fill: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx;" +
                        "-fx-padding: 0 0 0 10px;", // Padding: top, right, bottom, left
                green, buttonFont, buttonFontSize-4));

        // Align window title
        titleBar.setAlignment(Pos.CENTER_LEFT);
        title.setAlignment(Pos.CENTER); // Vertical alignment

        Region spacerTitle = new Region();
        HBox.setHgrow(spacerTitle, Priority.ALWAYS);

        // Close button
        Button closeButton = new Button("X");
        // Default button style (grey)
        String styleDefaultClose = String.format(
                "-fx-background-color: %s; " +
                "-fx-text-fill: %s; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 0;",
                greyDark, grey, greyDark);

        // Hover button style (red)
        String styleHoverClose = String.format(
                "-fx-background-color: %s; " +
                "-fx-text-fill: %s; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 0;",
                redMedium, redLight, redMedium);

        // Close button
        closeButton.setStyle(styleDefaultClose);  // Set default style

        // Set the button size to fit the header height
        closeButton.setMinSize(headerHeight - 10, headerHeight - 10);
        closeButton.setMaxSize(headerHeight - 10, headerHeight - 10);

        // Ensure text is centered within the button
        closeButton.setAlignment(Pos.CENTER);  // Center text in the button

        // Button hover effect
        closeButton.setOnMouseEntered(e ->
                closeButton.setStyle(styleHoverClose));
        closeButton.setOnMouseExited(e ->
                closeButton.setStyle(styleDefaultClose));
        closeButton.setOnAction(e -> primaryStage.close());
        
        
        HBox.setMargin(closeButton, new Insets(0, 5, 0, 0));
        closeButton.setMinSize(headerHeight - 10, headerHeight - 10);
        closeButton.setMaxSize(headerHeight - 10, headerHeight - 10);

        // Add title, spacer, and close button to the title bar
        titleBar.getChildren().addAll(title, spacerTitle, closeButton);
        root.setTop(titleBar);

        // Create instance of the ResizeWindow class
        AdjustWindow resizingHandler = new AdjustWindow(primaryStage, root);
        resizingHandler.initializeResizing(); // Initialize resizing functionality

        // ================================= Main Layout =================================
        VBox centerPane = new VBox(0); // VBox with spacing
        centerPane.setStyle(String.format("-fx-background-color: %s", greyDark));
        centerPane.setAlignment(Pos.TOP_CENTER); // Center the children horizontally

        centerPane.setPadding(new Insets(spacer, spacer, 0,  spacer));

        // ================================== Text Box ===================================
        TextArea textBox = new TextArea();
        VBox.setVgrow(textBox, Priority.ALWAYS); // Use all available vertical space
        textBox.setEditable(false);
        textBox.setWrapText(true);
        // Default style for the TextArea
        textBox.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 3px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx; " +
                        "-fx-control-inner-background: %s; " +
                        "-fx-background-insets: 0, 0, 0, 0;" +
                        "-fx-focus-color: transparent; " +
                        "-fx-highlight-fill: %s; " +
                        "-fx-highlight-text-fill: %s;" +
                        "-fx-faint-focus-color: transparent;",
                grey, black, green, buttonFont, buttonFontSize, grey, green, pink));

        // Set text
        textBox.setText("Words words words, words words words and more words.");


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
        String styleDefault = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx; ",
                black, green, green, buttonFont, buttonFontSize);

        // Button hover style
        String styleHover = String.format(
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

        button.setStyle(styleDefault);
        // Change the default button color when hovered over
        button.setOnMouseEntered(e -> {
            if (!isRecording) {
                button.setStyle(styleHover); // Apply hover style if not recording
            }
        });
        button.setOnMouseExited(e -> {
            if (!isRecording) {
                button.setStyle(styleDefault); // Apply default style if not recording
            }
        });

        // Toggle color on click
        button.setOnAction(e -> {
            isRecording = !isRecording; // Toggle state
            if (isRecording) {
                button.setText("Stop Recording");
                button.setStyle(recordingStyle); // Set the red color when recording
            } else {
                button.setText("Record");
                button.setStyle(styleDefault); // Set back to default (green) when stopped
            }
        });

        // Create a VBox to dynamically hold the button at the bottom
        VBox buttonContainer = new VBox(spacer);
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.setSpacing(0);
        buttonContainer.setPadding(new Insets(spacer*2, 0, spacer*2,  0));
        buttonContainer.getChildren().addAll(button);

        //============================== Add The Containers ==============================
        centerPane.getChildren().add(textBox);
        centerPane.getChildren().add(buttonContainer);
        root.setCenter(centerPane);

        // ================================ Miscellaneous ================================
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

