package com.mycompany.tetrisgame;

import javafx.stage.Stage;

public class TetrisGameApp {

    private static Stage primaryStage;

    // Setter used in App.java
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    // Allows all states (MenuState, PauseState…) to access the window
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
