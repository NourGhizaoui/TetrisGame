/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisgame.models.game;

import com.mycompany.tetrisgame.models.utils.GameLogger;

/**
 *
 * @author ASUS
 */
public class PauseState implements GameStatee {

    @Override
    public void start(GameContext context) {
        // Pas de start depuis pause
    }

    @Override
    public void pause(GameContext context) {
        // Déjà en pause
    }
/*
    @Override
    public void resume(GameContext context) {
        System.out.println("Reprise du jeu...");
        context.setState(new PlayingState());
    }*/
       @Override
    public void resume(GameContext context) {
        context.getController().getTimer().start();
        context.getController().getPauseButton().setText("Pause");
        GameLogger.getInstance().log("Jeu repris");
        context.setState(new PlayingState());
    }

    
    //Si le joueur quitte ou si le jeu se termine pendant la pause, on passe directement à GameOverState.
    @Override
    public void gameOver(GameContext context) {
        context.setState(new GameOverState());
    }

    @Override
    public void update(GameContext context) {
        // Animations de pause si nécessaire
    }
}
