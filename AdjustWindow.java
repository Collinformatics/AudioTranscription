package com.example.audiotranscription;

import javafx.scene.Cursor;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class AdjustWindow {
    private double initialX, initialY, initialWidth, initialHeight;
    private Stage primaryStage;
    private Region root;
    private String resizeMode;
    private boolean isResizing;  // Flag to control resizing state
    private boolean isMoving;    // Flag to control window moving
    private double xOffset, yOffset;

    public AdjustWindow(Stage primaryStage, Region root) {
        this.primaryStage = primaryStage;
        this.root = root;
        this.resizeMode = "";
        this.isResizing = false;  // Start with resizing off
        this.isMoving = false;    // Start with moving off
        initializeResizing();     // Call resizing logic
    }

    public void initializeResizing() {
        double borderThickness = 10; // Edge detection thickness

        // Resize cursor changes when moving over the edges or corners
        root.setOnMouseMoved(event -> {
            if (!isResizing && !isMoving) {  // Only update cursor when not resizing or moving
                boolean leftEdge = event.getX() <= borderThickness;
                boolean rightEdge = event.getX() >= root.getWidth() - borderThickness;
                boolean topEdge = event.getY() <= borderThickness;
                boolean bottomEdge = event.getY() >= root.getHeight() - borderThickness;

                if (leftEdge && topEdge) root.setCursor(Cursor.NW_RESIZE);  // Top-left corner
                else if (rightEdge && topEdge) root.setCursor(Cursor.NE_RESIZE);  // Top-right corner
                else if (leftEdge && bottomEdge) root.setCursor(Cursor.SW_RESIZE);  // Bottom-left corner
                else if (rightEdge && bottomEdge) root.setCursor(Cursor.SE_RESIZE);  // Bottom-right corner
                else if (leftEdge) root.setCursor(Cursor.W_RESIZE);  // Left edge
                else if (rightEdge) root.setCursor(Cursor.E_RESIZE);  // Right edge
                else if (topEdge) root.setCursor(Cursor.N_RESIZE);  // Top edge
                else if (bottomEdge) root.setCursor(Cursor.S_RESIZE);  // Bottom edge
                else root.setCursor(Cursor.DEFAULT);  // Default cursor when not near any edge
            }
        });

        // When the mouse is pressed, check the edge or corner being clicked
        root.setOnMousePressed(event -> {
            if (!isResizing && !isMoving) {  // Only process the press if we're not resizing or moving
                if (event.getX() <= 10 && event.getY() <= 10) {  // Top-left corner
                    resizeMode = "TOP_LEFT";
                } else if (event.getX() >= primaryStage.getWidth() - 10 && event.getY() <= 10) {  // Top-right corner
                    resizeMode = "TOP_RIGHT";
                } else if (event.getX() <= 10 && event.getY() >= primaryStage.getHeight() - 10) {  // Bottom-left corner
                    resizeMode = "BOTTOM_LEFT";
                } else if (event.getX() >= primaryStage.getWidth() - 10 && event.getY() >= primaryStage.getHeight() - 10) {  // Bottom-right corner
                    resizeMode = "BOTTOM_RIGHT";
                } else if (event.getX() <= 10) {  // Left edge
                    resizeMode = "LEFT";
                } else if (event.getX() >= primaryStage.getWidth() - 10) {  // Right edge
                    resizeMode = "RIGHT";
                } else if (event.getY() <= 10) {  // Top edge
                    resizeMode = "TOP";
                } else if (event.getY() >= primaryStage.getHeight() - 10) {  // Bottom edge
                    resizeMode = "BOTTOM";
                } else {
                    resizeMode = "";  // No resizing if not on an edge
                }

                if (!resizeMode.isEmpty()) {  // Only start resizing if we are on an edge or corner
                    isResizing = true;  // Start resizing
                    initialX = event.getScreenX();
                    initialY = event.getScreenY();
                    initialWidth = primaryStage.getWidth();
                    initialHeight = primaryStage.getHeight();
                } else {  // If not on an edge, prepare for moving the window
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                    isMoving = true;
                }
            }
        });

        // Handle the mouse dragging logic for resizing
        root.setOnMouseDragged(event -> {
            if (isResizing) {  // Only resize if the flag is set
                double deltaX = event.getScreenX() - initialX;
                double deltaY = event.getScreenY() - initialY;
                double newWidth, newHeight;

                switch (resizeMode) {
                    case "TOP":
                        newHeight = initialHeight - deltaY;
                        if (newHeight > 100) {
                            primaryStage.setHeight(newHeight);
                            primaryStage.setY(initialY + deltaY);  // Move the top
                        }
                        break;
                    case "BOTTOM":
                        newHeight = initialHeight + deltaY;
                        if (newHeight > 100) {
                            primaryStage.setHeight(newHeight);
                        }
                        break;
                    case "LEFT":
                        newWidth = initialWidth - deltaX;
                        if (newWidth > 100) {
                            primaryStage.setWidth(newWidth);
                            primaryStage.setX(initialX + deltaX);  // Move the left
                        }
                        break;
                    case "RIGHT":
                        newWidth = initialWidth + deltaX;
                        if (newWidth > 100) {
                            primaryStage.setWidth(newWidth);
                        }
                        break;
                    case "TOP_LEFT":
                        newWidth = initialWidth - deltaX;
                        newHeight = initialHeight - deltaY;
                        if (newWidth > 100) {
                            primaryStage.setWidth(newWidth);
                            primaryStage.setX(initialX + deltaX);  // Move the left
                        }
                        if (newHeight > 100) {
                            primaryStage.setHeight(newHeight);
                            primaryStage.setY(initialY + deltaY);  // Move the top
                        }
                        break;
                    case "TOP_RIGHT":
                        newWidth = initialWidth + deltaX;
                        newHeight = initialHeight - deltaY;
                        if (newWidth > 100) {
                            primaryStage.setWidth(newWidth);
                        }
                        if (newHeight > 100) {
                            primaryStage.setHeight(newHeight);
                            primaryStage.setY(initialY + deltaY);  // Move the top
                        }
                        break;
                    case "BOTTOM_LEFT":
                        newWidth = initialWidth - deltaX;
                        newHeight = initialHeight + deltaY;
                        if (newWidth > 100) {
                            primaryStage.setWidth(newWidth);
                            primaryStage.setX(initialX + deltaX);  // Move the left
                        }
                        if (newHeight > 100) {
                            primaryStage.setHeight(newHeight);
                        }
                        break;
                    case "BOTTOM_RIGHT":
                        newWidth = initialWidth + deltaX;
                        newHeight = initialHeight + deltaY;
                        if (newWidth > 100) {
                            primaryStage.setWidth(newWidth);
                        }
                        if (newHeight > 100) {
                            primaryStage.setHeight(newHeight);
                        }
                        break;
                }
            } else if (isMoving) {  // Only allow moving if not resizing
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        // Reset resizing or moving flag on mouse release
        root.setOnMouseReleased(event -> {
            isResizing = false;  // Stop resizing when the mouse is released
            isMoving = false;    // Stop moving when the mouse is released
        });
    }
}
