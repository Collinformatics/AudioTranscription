package com.example.audiotranscription;

import java.io.*;
import java.util.*;

public class Microphone {
    private static final String pythonExe = "C:/Users/Aurora/AppData/Local/Programs" +
            "/Python/Python313/python.exe";
    private static final String currentDir = System.getProperty("user.dir");

    // Use the CWD and concatenate the relative paths
    private static final String pathPython = currentDir + "/src/main/python/test.py";
    private static final String logFilePath = (currentDir +
            "/logs/log.txt").replace('/', '\\');

    // Python function
    public List<String> runPythonScript() {
        List<String> logLines = new ArrayList<>(); // To store each line of log file as elements
        StringBuilder output = new StringBuilder(); // To store the output from the Python script
        try {
            // Run the Python script without passing any parameters
            ProcessBuilder pythonScript = new ProcessBuilder(pythonExe, pathPython);
            pythonScript.inheritIO(); // Optional: Redirect input/output to the console

            // Start the process and wait for it to finish
            Process process = pythonScript.start();

            // Read the output from the Python script
            BufferedReader pythonReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = pythonReader.readLine()) != null) {
                output.append(line).append("\n"); // Append each line of output to the StringBuilder
            }

            int exitCode = process.waitFor(); // Wait for the process to finish

            // Log any issues with running the script
            if (exitCode != 0) {
                System.err.println("Python script exited with error code: " + exitCode);
            }

            // Now read the log file
            try {
                File logFile = new File(logFilePath);
                // System.out.println("Log file path: " + logFilePath);
                if (!logFile.exists()) {
                    System.err.println("Log file does not exist.");
                    // Return the output even if the log file is missing
                    return logLines;  // Return empty list if the file does not exist
                }

                // Create BufferedReader to read the log file
                BufferedReader logReader = new BufferedReader(new FileReader(logFile));
                String logLine;
                System.out.println("Loaded Txt:");
                while ((logLine = logReader.readLine()) != null) {
                    System.out.println("     " + logLine); // Print each line of the log file
                    logLines.add(logLine); // Add each line to the list
                }

                // Close the reader
                logReader.close();
            } catch (IOException e) {
                e.printStackTrace(); // Handle any file reading errors
            }

        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace(); // Handle any errors that occur when running the Python script
        }

        // Return the list of log lines
        return logLines;
    }
}
