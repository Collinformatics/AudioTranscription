package com.example.audiotranscription;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;
import javafx.scene.text.TextAlignment;


public class UI extends Application {
    // Define colors as strings
    public static final String red = "#FF0000";
    public static final String redLight = "#DE2828";
    public static final String redMedium = "#8D0000";
    public static final String redDark = "#560000";
    public static final String green = "#39FF14";
    public static final String greenMedium = "#085500";
    public static final String greenDark= "#003000";
    public static final String pink = "#EE00FF";
    public static final String grey = "#303030";
    public static final String greyDark = "#252525";
    public static final String black = "#202020";
    public static final String titleBarColor = "#171717";

    // Text parameters
    public static final String buttonFont = "Verdana";
    public static final int buttonFontSize = 22;

    // Program parameters
    public String pythonExe = "C:\\Users\\Yeti\\miniconda3\\envs\\envAudio\\python.exe";
    public int initialRun = 1;
    private boolean isRecording = false;



    private final double windowWidth = 600;
    private final double windowHeight = Screen.getPrimary().getVisualBounds().getHeight();
    private final double headerHeight = 40;
    private final double spacer = 10;
    public final int buttonWidth = 200;
    public final int buttonHeight = 60;
    public final int stateMicLabelWidth = 148;
    public final int stateMicWidth = 420;
    public final int stateMicHeight = 60;
    public static final String styleDefault = String.format(
            "-fx-background-color: %s; " +
            "-fx-text-fill: %s; " +
            "-fx-padding: 10px; " +
            "-fx-border-radius: 5px; " +
            "-fx-border-color: %s; " +
            "-fx-font-family: %s; " +
            "-fx-font-size: %spx; ",
            black, green, green, buttonFont, buttonFontSize);

    // Initialize microphone class
    Mic mic = new Mic();

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
                titleBarColor, grey);

        // Hover button style (red)
        String styleHoverClose = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 0;",
                redMedium, redLight);

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
        centerPane.setPadding(new Insets(0, spacer, 0,  spacer));

        // ============================== Microphone Status ==============================
        Label labelMic = new Label("Microphone:");
        labelMic.setMinWidth(stateMicLabelWidth);
        labelMic.setPrefWidth(stateMicLabelWidth);
        labelMic.setMaxWidth(stateMicLabelWidth);
        labelMic.setMinHeight(stateMicHeight);
        labelMic.setPrefHeight(stateMicHeight);
        labelMic.setMaxHeight(stateMicHeight);

        // Define label styles
        String styleLabelDefault = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-padding: 0px; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx; ",
                greyDark, green, buttonFont, buttonFontSize);
        labelMic.setStyle(styleLabelDefault);
        labelMic.setAlignment(Pos.CENTER_LEFT);
        labelMic.setTextAlignment(TextAlignment.CENTER);

        // Create the second input, for example a TextField
        Label labelMicState = new Label("Off");
        labelMicState.setMinWidth(stateMicWidth); // Adjust as necessary
        labelMicState.setPrefWidth(stateMicWidth); // Adjust as necessary
        labelMicState.setMaxWidth(buttonWidth); // Adjust as necessary
        labelMicState.setMinHeight(stateMicHeight);
        labelMicState.setPrefHeight(stateMicHeight);
        labelMicState.setMaxHeight(stateMicHeight);
        labelMicState.setStyle(styleLabelDefault);

        // Create an HBox to arrange the microphone labels
        HBox container = new HBox(spacer);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setSpacing(0);
        container.setPadding(new Insets(0, spacer, 0, 0));

        // Add the components to the HBox
        container.getChildren().addAll(labelMic, labelMicState);

        // Create a VBox to store the microphone state
        VBox containerLabel = new VBox(spacer);
        containerLabel.setAlignment(Pos.CENTER);
        containerLabel.setSpacing(0);
        containerLabel.setPadding(new Insets(0, 0, 0,  0));
        containerLabel.getChildren().addAll(container);
        
        
        // ================================== Text Box ===================================
        TextArea textBox = new TextArea();
        VBox.setVgrow(textBox, Priority.ALWAYS); // Use all available vertical space
        textBox.setEditable(false);
        textBox.setWrapText(true);
        textBox.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-background-insets: 0, 0, 0, 0;" +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 3px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-text-fill: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx; " +
                        "-fx-control-inner-background: %s; " +
                        "-fx-focus-color: transparent; " +
                        "-fx-highlight-fill: %s; " +
                        "-fx-highlight-text-fill: %s;" +
                        "-fx-faint-focus-color: transparent;",
                grey, black, green, buttonFont, buttonFontSize, grey, green, pink));

        // Set text
        textBox.setText("");

        // Set scroll bar color
        scene.getStylesheets().add(getClass().getResource(
                "/com/example/audiotranscription/styles.css").toExternalForm());


        // ================================ Record Button ================================
        Button button = new Button("Record");

        // Set button size
        button.setMinWidth(buttonWidth);
        button.setPrefWidth(buttonWidth);
        button.setMaxWidth(buttonWidth);
        button.setMinHeight(buttonHeight);
        button.setPrefHeight(buttonHeight);
        button.setMaxHeight(buttonHeight);

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
        String styleRecording = String.format(
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

        // Press the button
        button.setOnAction(e -> {
            isRecording = !isRecording; // Toggle state
            String pythonOutput;

            if (isRecording) {
                // Start recording
                button.setText("Stop Recording");
                button.setStyle(styleRecording);

                // Run python script
                Task<String> task =
                        mic.runPythonScript(pythonExe, 1, initialRun,
                                labelMicState, textBox, button);
                task.setOnSucceeded(event -> {
                    // Update the microphone label
                    String result = task.getValue();
                });
                task.setOnFailed(event -> {
                    // Update the TextArea with error message
                    labelMicState.setText("Error running Python script.");
                });

                // Start the background task
                new Thread(task).start();

                // Set initialRun to False
                if (initialRun == 1) {
                    initialRun = 0;
                }
            } else {
                // Stop recording
                button.setText("Record");
                button.setStyle(styleDefault);

                // Run python script
                Task<String> task =
                        mic.runPythonScript(pythonExe,0, initialRun,
                                labelMicState, textBox, button);
                task.setOnSucceeded(event -> {
                    // Update the microphone label
                    String result = task.getValue();
                });
                task.setOnFailed(event -> {
                    // Update the TextArea with error message
                    labelMicState.setText("Error running Python script.");
                });

                // Start the background task
                new Thread(task).start();
            }
        });

        // Create a VBox to store the button
        VBox containerButton = new VBox(spacer);
        containerButton.setAlignment(Pos.BOTTOM_CENTER);
        containerButton.setSpacing(0);
        containerButton.setPadding(new Insets(spacer*2, 0, spacer*2,  0));
        containerButton.getChildren().addAll(button);

        //============================== Add The Containers ==============================
        centerPane.getChildren().add(containerLabel);
        centerPane.getChildren().add(textBox);
        centerPane.getChildren().add(containerButton);
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

        // Focus the scene after the UI is loaded
        Platform.runLater(() -> scene.getRoot().requestFocus());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
