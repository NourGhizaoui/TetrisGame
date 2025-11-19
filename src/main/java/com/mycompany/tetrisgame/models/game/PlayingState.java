package com.mycompany.tetrisgame.models.game;

import com.mycompany.tetrisgame.TetrisGameApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlayingState implements GameStatee {

    @Override
    public void start(GameContext context) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/tetrisgame/views/GameView.fxml"));
            Stage stage = TetrisGameApp.getPrimaryStage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause(GameContext context) {
        context.setState(new PauseState());
    }

    @Override
    public void resume(GameContext context) {}

    @Override
    public void gameOver(GameContext context) {
        context.setState(new GameOverState());
    }

    @Override
    public void update(GameContext context) {}
}
