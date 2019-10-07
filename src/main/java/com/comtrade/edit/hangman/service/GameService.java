package com.comtrade.edit.hangman.service;

import com.comtrade.edit.hangman.model.Game;
import com.comtrade.edit.hangman.model.GameCode;
import com.comtrade.edit.hangman.model.Guess;
import com.comtrade.edit.hangman.model.Hangman;
import com.comtrade.edit.hangman.model.Player;
import com.comtrade.edit.hangman.model.PlayerCodes;
import com.comtrade.edit.hangman.model.GameState;
import com.comtrade.edit.hangman.model.UserAssign;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.hashids.Hashids;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public HashMap<String, Hangman> hangmanByGameCode = new HashMap<String, Hangman>();
    public int incrementer = 1;

    private void updatePlayerPoints(Hangman hangman, Guess guess, Boolean isGuessed) {
        for (Player player : hangman.getPlayers()) {
            if (player.getPlayerCode().equals(guess.getPlayerCode())) {
                if (isGuessed) {
                    player.setPoints(player.getPoints() + 1);
                } else {
                    player.setPoints(player.getPoints() - 1);
                    player.setLifesRemained(player.getLifesRemained() - 1);
                }
                break;
            }
        }
    }

    private void playTheMove(Guess guess) {
        Hangman hangman = this.getHangmanByPlayerCode(guess.getPlayerCode());
        if (hangman.getWord().contains(guess.getLetter())) {
            updatePlayerPoints(hangman, guess, true);
            hangman.setGuessWord(replace(hangman.getWord(), hangman.getGuessWord(), guess.getLetter()));
        } else {
            updatePlayerPoints(hangman, guess, false);
        }
    }

    private String replace(String word, String guess, String letter) {
        StringBuilder guessStringBuilder = new StringBuilder(guess);
        for (int i = 0; i < word.length(); i++) {
            if (String.valueOf(word.charAt(i)).equalsIgnoreCase(letter)) {
                guessStringBuilder.setCharAt(i, word.charAt(i));
            }
        }

        return guessStringBuilder.toString();
    }

    public Hangman createGame(String word, Integer numberOfplayers) {
        Hangman hangman = new Hangman(word, numberOfplayers);

        Hashids hashids = new Hashids("this is my salt", 4, "abcdefghijklmnopqrstuvwxyz1234567890");
        ArrayList<Player> players = new ArrayList<Player>();

        String gameCode = hashids.encode(incrementer++);
        hangman.setCode(gameCode);

        for (int i = incrementer; i < hangman.getNumberOfPlayers() + incrementer; i++) {
            String playerCode = hashids.encode(i);
            Player player = new Player();
            player.setPlayerCode(playerCode);
            player.setGameCode(gameCode);
            players.add(player);
        }

        incrementer += hangman.getNumberOfPlayers();
        hangman.setPlayers(players);

        hangmanByGameCode.put(gameCode, hangman);

        return hangman;
    }

    public PlayerCodes getPlayerCodes(String gameCode) {
        System.out.println("2");
        PlayerCodes codes = new PlayerCodes();
        ArrayList<Player> players = new ArrayList<Player>();

        for (Map.Entry<String, Hangman> entry : hangmanByGameCode.entrySet()) {
            if (entry.getKey().equals(gameCode)) {
                players = entry.getValue().getPlayers();
            }
        }

        for (Player p : players) {
            codes.addPlayerCode(p.getPlayerCode());
        }

        return codes;

    }

    public Game assignUser(UserAssign userAssign) {
        Game game = new Game();
        Boolean breaking = false;
        for (Map.Entry<String, Hangman> entry : hangmanByGameCode.entrySet()) {
            ArrayList<Player> players = entry.getValue().getPlayers();
            for (Player p : players) {
                if (p.getPlayerCode().equals(userAssign.getPlayerCode())) {
                    p.setUsername(userAssign.getUsername());
                    game.setGameCode(entry.getKey());
                    breaking = true;
                    break;
                }
            }
            if (breaking) {
                break;
            }
        }
        if (!breaking) {
            return null;
        }

        GameState gameState = new GameState();
        Hangman hangman = this.getHangmanByPlayerCode(userAssign.getPlayerCode());
        gameState.setGuessWord(hangman.getGuessWord());
        gameState.setPlayers(hangman.getPlayers());
        gameState.setNextPlayerCode(hangman.getCurrentPlayerCode());

        game.setGameState(gameState);

        return game;
    }

    public Hangman getHangmanByPlayerCode(String playerCode) {
        Hangman hangman = null;
        Boolean breaking = false;
        for (Map.Entry<String, Hangman> entry : hangmanByGameCode.entrySet()) {
            ArrayList<Player> players = entry.getValue().getPlayers();
            for (Player p : players) {
                if (p.getPlayerCode().equals(playerCode)) {
                    hangman = entry.getValue();
                    breaking = true;
                    break;
                }
            }
            if (breaking) {
                break;
            }
        }

        return hangman;

    }

    public GameState doAllTheWork(Guess guess) {
        playTheMove(guess);
        Hangman hangman = this.getHangmanByPlayerCode(guess.getPlayerCode());

        // switch to next player
        hangman.switchToNextPlayer();

        // return state
        GameState state = new GameState();
        state.setGuessWord(hangman.getGuessWord());
        state.setPlayers(hangman.getPlayers());
        state.setNextPlayerCode(hangman.getCurrentPlayerCode());

        return state;
    }

    public GameState getGameStateByGameCode(String gameCode) {
        Hangman hangman = hangmanByGameCode.get(gameCode);

        GameState gameState = new GameState();
        gameState.setGuessWord(hangman.getGuessWord());
        gameState.setPlayers(hangman.getPlayers());
        gameState.setNextPlayerCode(hangman.getCurrentPlayerCode());

        return gameState;
    }

}
