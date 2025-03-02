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
    public String pythonExe = "C:\path\to\python.exe";
    public int initialRun = 1;
    private boolean isRecording = false;

    // UI parameters
    private final double windowWidth = 600;
    private final double windowHeight = Screen.getPrimary().getVisualBounds().getHeight();
    private final double headerHeight = 40;
    private final double spacer = 10;
    public final int buttonWidth = 200;
    public final int buttonHeight = 60;
    public final int stateMiscWidth = 260;
    public final int stateMiscHeight = 50;
    public final int stateMicLabelWidth = 200;
    public static final String colorDefault = green;
    public static final String colorDefaultBG = black;
    public static final String colorRecording = red;
    public static final String colorRecordingBG = redDark;
    public static final String colorProcessing = pink;
    public static final String colorProcessingBG = colorDefaultBG;

    public static final String styleButtonProcessing = String.format(
            "-fx-background-color: %s; " +
                   "-fx-border-radius: 5px; " +
                    "-fx-border-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-padding: 10px; " +
                    "-fx-font-family: %s; " +
                    "-fx-font-size: %spx; ",
            black, pink, pink, buttonFont, buttonFontSize);

    // Define functions
    public void toggleRecordingState() {
        if (isRecording) {
            isRecording = !isRecording;
            System.out.println("isRecording state changed to: " + isRecording);
        }
    }

    // Initialize microphone class
    Mic mic = new Mic(this);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED); // Remove OS title bar
        primaryStage.setResizable(true);  // Ensure the stage can be resized

        // ========================== Initialize Root Container ==========================
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + grey + ";"); // Window background
        root.setPadding(Insets.EMPTY);

        Scene scene = new Scene(root, windowWidth, windowHeight);

        // Position window on the right side of the monitor
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double xPosition = screenWidth - windowWidth;
        primaryStage.setX(xPosition);
        // primaryStage.setX(0); // To move the window to the left side use this
        primaryStage.setY(0);

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
        String styleButtonHoverClose = String.format(
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
                closeButton.setStyle(styleButtonHoverClose));
        closeButton.setOnMouseExited(e ->
                closeButton.setStyle(styleDefaultClose));
        closeButton.setOnAction(e -> primaryStage.close());
        
        
        HBox.setMargin(closeButton, new Insets(0, 0, 0, 0));
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

        // =============================== UI Information ================================
        String styleLabelMicOff = String.format(
            "-fx-text-fill: %s; " +
                "-fx-padding: 0px; " +
                "-fx-font-family: %s; " +
                "-fx-font-size: %spx; ",
            UI.colorDefault, UI.buttonFont, UI.buttonFontSize);
        
        // Miscellaneous label
        Label labelMisc = new Label("");
        labelMisc.setMinWidth(stateMiscWidth); // Adjust as necessary
        labelMisc.setPrefWidth(stateMiscWidth); // Adjust as necessary
        labelMisc.setMaxWidth(buttonWidth); // Adjust as necessary
        labelMisc.setMinHeight(stateMiscHeight);
        labelMisc.setPrefHeight(stateMiscHeight);
        labelMisc.setMaxHeight(stateMiscHeight);
        labelMisc.setStyle(styleLabelMicOff);

        // Microphone state
        Label labelMic = new Label("Microphone: Off");
        labelMic.setMinWidth(stateMicLabelWidth);
        labelMic.setPrefWidth(stateMicLabelWidth);
        labelMic.setMaxWidth(stateMicLabelWidth);
        labelMic.setMinHeight(stateMiscHeight);
        labelMic.setPrefHeight(stateMiscHeight);
        labelMic.setMaxHeight(stateMiscHeight);
        labelMic.setStyle(styleLabelMicOff);
        labelMic.setAlignment(Pos.CENTER_RIGHT);
        labelMic.setTextAlignment(TextAlignment.CENTER);

        // Create a spacer (empty Region that expands)
        Region spacerRegion = new Region();
        HBox.setHgrow(spacerRegion, Priority.ALWAYS);

        // Create an HBox to arrange the microphone labels
        HBox container = new HBox(spacer);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(0, 0, 0, 0));

        // Add the components to the HBox
        container.getChildren().addAll(labelMisc, spacerRegion,labelMic);

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

        String styleButtonMicOff = String.format(
        "-fx-background-color: %s; " +
                "-fx-text-fill: %s; " +
                "-fx-padding: 0px; " +
                "-fx-font-family: %s; " +
                "-fx-font-size: %spx; ",
        colorDefaultBG, colorDefault, buttonFont, buttonFontSize);
        button.setStyle(styleButtonMicOff);

        // Button hover style
        String styleButtonHover = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %dpx; ",
                greenDark, green, green, buttonFont, buttonFontSize);

        // Recording button style
        String styleButtonMicStartup = String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: %s; " +
                        "-fx-padding: 10px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: %s; " +
                        "-fx-font-family: %s; " +
                        "-fx-font-size: %spx; ",
                black, pink, pink, buttonFont, buttonFontSize);
        
        // Change the default button color when hovered over
        button.setOnMouseEntered(e -> {
            if (!isRecording) {
                button.setStyle(styleButtonHover); // Apply hover style if not recording
            }
        });
        button.setOnMouseExited(e -> {
            if (!isRecording) {
                String currentButtonStyle = button.getStyle();
                button.setStyle(currentButtonStyle); // Default style if not recording
            }
        });

        // Load the conversation log
        if (initialRun == 1) {
            Task<String> task =
                        mic.runPythonScript(pythonExe, 0, initialRun,
                                labelMic, textBox, button);

            // Handle successful execution
            task.setOnSucceeded(event -> {
                // UI updates handled inside runPythonScript method
            });

            // Handle failure
            task.setOnFailed(event -> {
                labelMic.setText("Error loading messages.");
            });

            // Start the background thread
            new Thread(task).start();

            // Set initialRun to 0 after loading messages
            initialRun = 0;
        }

        // Press the button
        button.setOnAction(e -> {
            isRecording = !isRecording; // Toggle state

            // String pythonOutput;
            if (isRecording) {
                // Start recording
                button.setText("Activating Mic");
                button.setStyle(styleButtonMicStartup);

                // Run python script
                Task<String> task =
                        mic.runPythonScript(pythonExe, 1, initialRun,
                                labelMic, textBox, button);

                // Start the background task
                new Thread(task).start();
            } else {
//                // Stop recording
//                String currentButtonStyle = button.getStyle();
//                button.setText("Record");
//                button.setStyle(currentButtonStyle);
//
//                // Run python script
//                Task<String> task =
//                        mic.runPythonScript(pythonExe,0, initialRun,
//                                labelMic, textBox, button);
//
//                // Start the background task
//                new Thread(task).start();
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
