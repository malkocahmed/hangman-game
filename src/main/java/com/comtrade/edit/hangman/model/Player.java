package com.comtrade.edit.hangman.model;

/**
 *
 * @author user
 */
public class Player {
    private String username;
    private String playerCode;
    private String gameCode;
    private int lifesRemained;
    private int points;
    
    public Player() {
        this.lifesRemained = Hangman.ATTEMPTS;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlayerCode() {
        return playerCode;
    }
    
    public void setPlayerCode(String playerCode) {
        this.playerCode = playerCode;
    }
    
    public String getGameCode() {
        return gameCode;
    }
    
    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }
    
    public int getLifesRemained() {
        return lifesRemained;
    }

    public void setLifesRemained(int lifesRemained) {
        this.lifesRemained = lifesRemained < 0 ? 0 : lifesRemained;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points < 0 ? 0 : points;
    }
}
