
package com.mycompany.tetrisgame.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button startButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button quitButton;

    @FXML
    public void initialize() {
        startButton.setOnAction(e -> startGame());
        settingsButton.setOnAction(e -> openSettings());
        quitButton.setOnAction(e -> quitGame());
    }

private void startGame() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tetrisgame/GameView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}


    private void openSettings() {
        System.out.println("Settings clicked!"); // futur implementation
    }

    private void quitGame() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
}
