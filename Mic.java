package com.example.audiotranscription;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.scene.control.Label;


public class Mic {
    private static final String currentDir = System.getProperty("user.dir");
    private static final String pathPython = currentDir + "/src/main/python/mic.py";

    // Define color schemes
    String styleLabelInactive = String.format(
            "-fx-text-fill: %s; " +
                    "-fx-padding: 0px; " +
                    "-fx-font-family: %s; " +
                    "-fx-font-size: %spx; ",
            UI.greenMedium, UI.buttonFont, UI.buttonFontSize);
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

    public Task<String> runPythonScript(String pythonExe, int flagTest, int flagNew,
                                        Label labelMicState) {
        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                // Run python script
                ProcessBuilder pythonScript = new ProcessBuilder(pythonExe, pathPython,
                        Integer.toString(flagTest), Integer.toString(flagNew));
                pythonScript.redirectErrorStream(true); // Merge stdout and stderr

                Process process = pythonScript.start();

                // Continuously read the output while the process is running
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String output;
                StringBuilder fullOutput = new StringBuilder();

                while ((output = reader.readLine()) != null) {
                    final String outputCopy = output;

                    System.out.println("Py Output: " + outputCopy);

                    // Use Platform.runLater to update the UI thread
                    Platform.runLater(() -> {
                        // Update the microphone label
                        if (outputCopy.contains("Mic On")) {
                            labelMicState.setText("On");
                            labelMicState.setStyle(styleLabelMicOn);
                        } else if (outputCopy.contains("Processing")) {
                            labelMicState.setText("Processing Audio");
                            labelMicState.setStyle(styleLabelProcessing);
                        } else if (outputCopy.contains("Mic Off")) {
                            labelMicState.setText("Off");
                            labelMicState.setStyle(styleLabelInactive);
                        }
                    });
                    fullOutput.append(output).append("\n");
                }
                reader.close();

                // Wait for the process to finish
                process.waitFor();
                return fullOutput.toString().trim();
            }
        };
        return task;
    }
}

