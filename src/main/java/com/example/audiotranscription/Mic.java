package com.example.audiotranscription;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class Mic {
    private static final String currentDir = System.getProperty("user.dir");
    private static final String pathPython = currentDir + "/src/main/python/mic.py";

    // Microphone label styles
    String styleLabelMicOn = String.format(
            "-fx-text-fill: %s; " +
                "-fx-padding: 0px; " +
                "-fx-font-family: %s; " +
                "-fx-font-size: %spx; ",
            UI.red, UI.buttonFont, UI.buttonFontSize);
    String styleLabelProcessing = String.format(
            "-fx-text-fill: %s; " +
                    "-fx-padding: 0px; " +
                    "-fx-font-family: %s; " +
                    "-fx-font-size: %spx; ",
            UI.pink, UI.buttonFont, UI.buttonFontSize);
                    //  "-fx-border-radius: 5px; " +
                    //  "-fx-border-color: %s; " +

//    // Declare the button globally
//    public Button button;

//    public void initializeUI() {
//        // Initialize the button
//        button = new Button("Record");
//    }


    // Error handling method to update the TextArea in the UI
    public void updateTextAreaWithError(String errorMessage, TextArea textArea) {
        Platform.runLater(() -> {
            textArea.appendText("\n" + errorMessage + "\n");
        });
    }

    public Task<String> runPythonScript(String pythonExe, int flagRecordAudio,
                                        int initializeUI, Label labelMic,
                                        TextArea textArea, Button button) {
        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                try {
                    ProcessBuilder pythonScript =
                            new ProcessBuilder(pythonExe, pathPython,
                                    Integer.toString(flagRecordAudio),
                                    Integer.toString(initializeUI));
                    pythonScript.redirectErrorStream(true); // Merge stdout and stderr

                    Process process = pythonScript.start();
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(process.getInputStream()));
                    String output;
                    StringBuilder fullOutput = new StringBuilder();
                    StringBuilder errorBuffer = new StringBuilder();

                    boolean isError = false;

                    while ((output = reader.readLine()) != null) {
                        final String outputCopy = output;
                        System.out.println(outputCopy); // Print output for debugging

                        // Check if it's an error (traceback)
                        if (outputCopy.startsWith("Traceback (most recent call last):")) {
                            isError = true; // Start capturing traceback lines
                            errorBuffer.setLength(0); // Clear buffer
                        }

                        if (isError) {
                            // Append traceback lines
                            errorBuffer.append(outputCopy).append("\n");
                        }

                        // If traceback ends, send the full error to the UI
                        if (isError && outputCopy.trim().isEmpty()) {
                            isError = false; // End of error message
                            String fullError = errorBuffer.toString();
                            Platform.runLater(() -> updateTextAreaWithError(
                                    "\n" + fullError, textArea));
                        }

                        // Update UI
                        Platform.runLater(() -> {
                            if (outputCopy.contains("Mic On")) {
                                labelMic.setText("Microphone: On");
                                labelMic.setStyle(styleLabelMicOn);
                            } else if (outputCopy.contains("Processing")) {
                                labelMic.setText("Processing Audio");
                                labelMic.setStyle(styleLabelProcessing);
                            } else if (outputCopy.contains("Mic Off")) {
                                labelMic.setText("Microphone: Off");
                                labelMic.setStyle(UI.styleLabelMicOff);
                                button.setText("Record");
                                button.setStyle(UI.styleButtonDefault);
                            } else if (outputCopy.startsWith("_text_")) {
                                // Remove tag and display the message
                                String textMessage = outputCopy.substring(6);
                                textArea.appendText(textMessage + "\n");
                            }
                        });

                        fullOutput.append(output).append("\n");
                    }
                    reader.close();

                    // If an error was still being captured, flush it
                    if (errorBuffer.length() > 0) {
                        String fullError = errorBuffer.toString();
                        Platform.runLater(() -> updateTextAreaWithError(
                                "\n" + fullError, textArea));
                    }

                    process.waitFor();
                    return fullOutput.toString().trim();
                } catch (Exception e) {
                    Platform.runLater(() -> updateTextAreaWithError(
                            "\nJava Error: " + e.getMessage(), textArea));
                    return null;
                }
            }
        };
        return task;
    }
}
