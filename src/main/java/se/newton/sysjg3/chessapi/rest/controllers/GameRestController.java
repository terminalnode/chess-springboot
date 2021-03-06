package se.newton.sysjg3.chessapi.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.helpers.ChessMove;
import se.newton.sysjg3.chessapi.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameRestController {
  private GameService gameService;

  //----- Constructor ------//
  @Autowired
  public GameRestController(GameService gameService) {
    this.gameService = gameService;
  }

  //----- GET Mappings -----//
  @GetMapping("/games")
  public List<Game> getAllCurrentPlayerGamesFromToken(
      @RequestHeader(value="Token") String tokenString) {
    return gameService.getCurrentPlayerGamesFromToken(tokenString);
  }

  @GetMapping("/games/{gameId}")
  public Game getCurrentPlayerGamesFromGameId(
      @RequestHeader(value="Token") String tokenString,
      @PathVariable long gameId) {
    return gameService.getCurrentPlayerGameFromGameId(tokenString, gameId);
  }

  //----- POST Mappings -----//
  @PostMapping("/games/{gameId}")
  public ChessMove makeMoveInGame(
      @RequestHeader(value="Token") String tokenString,
      @RequestBody ChessMove move,
      @PathVariable long gameId) {
    gameService.makeMove(move, gameId, tokenString);
    return move;
  }
}