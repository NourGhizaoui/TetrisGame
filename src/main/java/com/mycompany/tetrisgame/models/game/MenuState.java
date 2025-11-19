
package com.mycompany.tetrisgame.models.game;

//MenuState est l’état du jeu quand on est dans le menu principal (avant que le joueur commence à jouer).
//MenuState = écran de démarrage
//Dans cet état, plein d’actions sont impossibles (pause, resume…), et une seule action est logique :
//start() → commencer la partie → passer en PlayingState.
public class MenuState implements GameStatee {

    /*
     * @Override
     * public void start(GameContext context) {
     * System.out.println("Démarrage du jeu depuis le menu...");
     * 
     * context.setState(new PlayingState());
     * }
     */

    @Override
    public void start(GameContext context) {
        // Démarre le jeu depuis le menu
        context.setState(new PlayingState());
        context.getController().getTimer().start();
        context.getController().getPauseButton().setText("Pause");
        System.out.println("Jeu commencé depuis le menu");
    }

    @Override
    public void pause(GameContext context) {
        // Pas de pause possible dans le menu
    }

    @Override
    public void resume(GameContext context) {
        // Pas de reprise possible dans le menu
    }

    @Override
    public void gameOver(GameContext context) {
        // Menu ne peut pas déclencher GameOver
    }

    @Override
    public void update(GameContext context) {
        // Afficher animations menu si nécessaire
    }
}