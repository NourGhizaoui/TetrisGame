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
public class PlayingState implements GameStatee {

    @Override
    public void start(GameContext context) {
        // Déjà en cours de jeu
    }

    /*
     * @Override
     * public void pause(GameContext context) {
     * System.out.println("Jeu en pause...");
     * context.setState(new PauseState());
     * }
     */

    @Override
    public void pause(GameContext context) {
        context.getController().getTimer().stop();
        context.getController().getPauseButton().setText("Resume");
        GameLogger.getInstance().log("Jeu en pause");
        context.setState(new PauseState());
    }
    

    @Override
    public void resume(GameContext context) {
        // Déjà en jeu
    }

    // ➡️ Quand le joueur perd → transition vers l’état “GameOver”.
    /*
     * @Override
     * public void gameOver(GameContext context) {
     * System.out.println("Game Over !");
     * context.setState(new GameOverState());
     * }
     */

    @Override
    public void gameOver(GameContext context) {
        context.getController().getTimer().stop();
        context.setState(new GameOverState());
        System.out.println("Game Over !");
    }

    @Override
    public void update(GameContext context) {
        // Ici, boucle principale du jeu : déplacer pièces, vérifier collisions, lignes
        // complètes...
    }

    // ➡️ C’est la méthode la plus importante.
    // dans cet état, l’update doit gérer :

    // chute automatique de la pièce + gestion des inputs (gauche, droite,
    // rotation…) + détection des collisions
    // verrouillage de la pièce au sol + apparition d’une nouvelle pièce +
    // suppression des lignes complètes + vérification du game over
}
