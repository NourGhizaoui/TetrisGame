/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisgame.models.game;

//GameOverState est l’état du jeu lorsque le joueur a perdu.
public class GameOverState implements GameStatee {
    /*
     * @Override
     * public void start(GameContext context) {
     * System.out.println("Redémarrage du jeu...");
     * context.setState(new PlayingState());
     * }
     */

    @Override
    public void start(GameContext context) {
        // recommencer le jeu
        context.getController().getTimer().stop();
        context.getController().getPauseButton().setText("Pause");
        context.setState(new PlayingState());
        System.out.println("Jeu redémarré");
    }

    @Override
    public void pause(GameContext context) {
        // Impossible en GameOver
    }

    @Override
    public void resume(GameContext context) {
        // Impossible en GameOver
    }

    @Override
    public void gameOver(GameContext context) {
        // Déjà en GameOver
    }

    @Override
    public void update(GameContext context) {
        // Afficher écran Game Over
        // Ici tu peux afficher : “Game Over” + Le score final + Animation du Game Over
    }
}