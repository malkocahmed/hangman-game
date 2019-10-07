package com.comtrade.edit.hangman.model;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class GameState {
    private String guessWord;
    private ArrayList<Player> players;
    private String nextPlayerCode;

    public String getGuessWord() {
        return guessWord;
    }

    public void setGuessWord(String guessWord) {
        this.guessWord = guessWord;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getNextPlayerCode() {
        return nextPlayerCode;
    }

    public void setNextPlayerCode(String nextPlayerCode) {
        this.nextPlayerCode = nextPlayerCode;
    }
    
    
}
