
package com.mycompany.tetrisgame.models.game;


public interface GameStatee {
    void start(GameContext context);
    void pause(GameContext context);
    void resume(GameContext context);
    void gameOver(GameContext context);
    void update(GameContext context);
}