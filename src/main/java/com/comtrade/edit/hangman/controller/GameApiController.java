package com.comtrade.edit.hangman.controller;

import com.comtrade.edit.hangman.model.CreateGame;
import com.comtrade.edit.hangman.model.Game;
import com.comtrade.edit.hangman.model.GameCode;
import com.comtrade.edit.hangman.model.GameState;
import com.comtrade.edit.hangman.model.Hangman;
import com.comtrade.edit.hangman.model.PlayerCodes;
import com.comtrade.edit.hangman.model.UserAssign;
import com.comtrade.edit.hangman.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/game")
public class GameApiController {

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping(path = "/create-game", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PlayerCodes createGame(@RequestBody(required = false) CreateGame createGame) {
        Hangman hangman = gameService.createGame(createGame.getWord().toUpperCase(), createGame.getNumberOfPlayers());

        return gameService.getPlayerCodes(hangman.getCode());
    }

    @PostMapping(path = "/join-game", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GameCode joinGame(@RequestBody(required = false) UserAssign userAssign) {
        Game game = gameService.assignUser(userAssign);
        GameState gameState = gameService.getGameStateByGameCode(game.getGameCode());
        simpMessagingTemplate.convertAndSend("/topic/hangman" + game.getGameCode(), gameState);
        return new GameCode(game.getGameCode());
    }

    @PostMapping(path = "/game-state", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GameState findGame(@RequestBody(required = false) GameCode gameCode) {
        GameState gameState = gameService.getGameStateByGameCode(gameCode.getGameCode());
        return gameState;
    }

}
