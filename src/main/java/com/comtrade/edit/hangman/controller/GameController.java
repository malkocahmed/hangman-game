package com.comtrade.edit.hangman.controller;

import com.comtrade.edit.hangman.model.GameCode;
import com.comtrade.edit.hangman.model.Hangman;
import com.comtrade.edit.hangman.model.Guess;
import com.comtrade.edit.hangman.model.GameState;
import com.comtrade.edit.hangman.service.GameService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@MessageMapping("/hangman")
public class GameController {
    //private static final Logger logger = LoggerFactory.getLogger(ChatControler.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

//    @MessageMapping("/addGame")
//    @SendTo("/topic/new")
//    public Hangman sendNewGame(@Payload Hangman hangman) {
//        Hashids hashids = new Hashids("this is my salt", 4, "abcdefghijklmnopqrstuvwxyz1234567890");
//        ArrayList<Player> players = new ArrayList<Player>();
//
//        String gameCode = hashids.encode(this.gameService.incrementer++);
//        hangman.setCode(gameCode);
//
//        for (int i = this.gameService.incrementer; i < hangman.getNumberOfPlayers() + this.gameService.incrementer; i++) {
//            String playerCode = hashids.encode(i);
//            Player player = new Player();
//            player.setPlayerCode(playerCode);
//            player.setGameCode(gameCode);
//            players.add(player);
//        }
//
//        this.gameService.incrementer += hangman.getNumberOfPlayers();
//        hangman.setPlayers(players);
//
//        this.gameService.hangmanByGameCode.put(gameCode, hangman);
//
//        return hangman;
//    }

//    @MessageMapping("/findGame")
//    public void findGame(@Payload Player player) {
//        String gameCode = null;
//        Boolean found = false;
//
//        for (Map.Entry<String, Hangman> entry : this.gameService.hangmanByGameCode.entrySet()) {
//            Hangman hangman = entry.getValue();
//            ArrayList<Player> players = hangman.getPlayers();
//
//            for (Player p : players) {
//                if (p.getPlayerCode().equals(player.getPlayerCode())) {
//                    p.setUsername(player.getUsername());
//                    gameCode = p.getGameCode();
//                    found = true;
//                    break;
//                }
//            }
//            if (found) {
//                break;
//            }
//        }
//
//        player.setGameCode(gameCode);
//        simpMessagingTemplate.convertAndSend("/topic/player" + player.getPlayerCode(), player);
//
//    }
    

    @MessageMapping("/guess")
    public GameState playGame(@Payload Guess guess) {
       Hangman hangman = gameService.getHangmanByPlayerCode(guess.getPlayerCode()); 
        
       GameState state = gameService.doAllTheWork(guess);

       simpMessagingTemplate.convertAndSend("/topic/hangman" + hangman.getCode(), state);
       
       return state;
    }
}