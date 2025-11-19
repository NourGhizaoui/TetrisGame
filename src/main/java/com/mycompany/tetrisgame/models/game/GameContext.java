/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisgame.models.game;

import com.mycompany.tetrisgame.controllers.Controller;
import com.mycompany.tetrisgame.models.utils.GameLogger;

/**
 *
 * @author ASUS
 */
public class GameContext {  //le cerveau du jeu
    
    private GameStatee currentState;  //GameContext est l'objet principal qui gère le cycle de vie du jeu.


    public GameContext() {
        // défini l’état initial du jeu : Quand le jeu démarre, il commence dans l’écran de Menu.
        this.currentState = new MenuState();
    }

    public void setState(GameStatee state) {
        this.currentState = state; //➡ Elle remplace l’état courant par le nouveau.

        GameLogger.getInstance().log("Changement d'état : " + state.getClass().getSimpleName()); //Elle écrit dans un Logger pour suivre ce qui se passe (excellent pour débogage !).
    }

    public GameStatee getState() {
        return currentState;
    }

    // Méthodes pour déléguer à l'état courant
    public void start() {
        currentState.start(this);
    }

    public void pause() {
        currentState.pause(this);
    }

    public void resume() {
        currentState.resume(this);
    }

    public void gameOver() {
        currentState.gameOver(this);
    }

    public void update() {
        currentState.update(this);
    }
    
    private Controller controller;

public void setController(Controller controller) {
    this.controller = controller;
}

public Controller getController() {
    return controller;
}

}