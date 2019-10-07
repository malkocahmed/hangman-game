package com.comtrade.edit.hangman.model;

/**
 *
 * @author user
 */
public class Game {

    private String gameCode;
    private GameState gameState;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}
