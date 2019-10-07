package com.comtrade.edit.hangman.model;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Hangman {

    private String code; // code of the game
    private final String word; // word that needs to be guessed
    private Integer numberOfPlayers; // maximum number of players
    private ArrayList<Player> players; // array list of players playing this game
    private String guessWord;
    private int numberOfPlayersLoggedIn;
    private Player currentPlayer;

    public static final int ATTEMPTS = 6;

    public Hangman(String word, Integer numberOfPlayers) {

        this.word = word;
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<>();
        guessWord = "";
        for (int i = 0; i < word.length(); i++) {
            guessWord += "_";
        }
        numberOfPlayersLoggedIn = 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWord() {
        return word;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayer = this.players.get(0);
    }

    public int getNumberOfPlayersLoggedIn() {
        numberOfPlayersLoggedIn = 0;
        for (Player p : players) {
            if (p.getUsername() == null) {
                numberOfPlayersLoggedIn++;
            }
        }

        return numberOfPlayersLoggedIn;
    }

    public String getGuessWord() {
        return guessWord;
    }

    public void setGuessWord(String guessWord) {
        this.guessWord = guessWord;
    }

    public String getCurrentPlayerCode() {
        return this.currentPlayer.getPlayerCode();
    }

    public void switchToNextPlayer() {
        int numberOfPlayer = this.players.size();
        do {
            int indexOfNextPlayer = this.players.indexOf(this.currentPlayer) + 1;
            indexOfNextPlayer = indexOfNextPlayer >= this.players.size() ? 0 : indexOfNextPlayer;
            this.currentPlayer = this.players.get(indexOfNextPlayer);
            numberOfPlayer--;
        } while (currentPlayer.getLifesRemained() <= 0 && numberOfPlayer > 0);
    }

//    public String getNextPlayerCode(String currentPlayerCode) {
//        String next = null;
//        for (int i = 0; i < this.players.size(); i++) {
//            if (players.get(i).getPlayerCode().equals(currentPlayerCode)) {
//                if (i != players.size() - 1) {
//                    next = players.get(i + 1).getPlayerCode();
//                } else {
//                    next = players.get(0).getPlayerCode();
//                }
//            }
//        }
//
//        return next;
//
//    }
}
