package com.comtrade.edit.hangman.model;
//import org.hashids;

/**
 *
 * @author user
 */
public class Guess {
    private String playerCode;
    private String letter;
    
    public String getPlayerCode() {
        return playerCode;
    }
    
    public void setPlayerCode() {
        this.playerCode = playerCode;
    }
    
    public String getLetter() {
        return letter;
    }
    
    public void setLetter(String letter) {
        this.letter = letter;
    }
}
