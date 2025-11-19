
package com.mycompany.tetrisgame.models.game;


//L’interface sert à dire :
//Tous les états du jeu : MenuState,PlayingState,PauseState,GameOverState,doivent avoir ces actions, mais ils vont chacun les exécuter différemment.”
public interface GameStatee {
    void start(GameContext context);
    void pause(GameContext context);
    void resume(GameContext context);
    void gameOver(GameContext context);
    void update(GameContext context);  // boucle du jeu
}