package com.mycompany.tetrisgame.models.game;

import com.mycompany.tetrisgame.models.utils.GameLogger;

public class GameContext {

    private GameStatee currentState;

    public GameContext() {
        // Default state when game starts
        this.currentState = new MenuState();
    }

    public void setState(GameStatee state) {
        GameLogger.getInstance().log("Switching to: " + state.getClass().getSimpleName());
        this.currentState = state;
        this.currentState.start(this);
    }

    public GameStatee getState() {
        return currentState;
    }

    // Delegations
    public void start()   { currentState.start(this); }
    public void pause()   { currentState.pause(this); }
    public void resume()  { currentState.resume(this); }
    public void gameOver(){ currentState.gameOver(this); }
    public void update()  { currentState.update(this); }
}
