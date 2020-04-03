package se.newton.sysjg3.chessapi.rest.controllers;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.helpers.ChessMove;
import se.newton.sysjg3.chessapi.service.ChallengeService;
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
  public List<Game> getAllCurrentPlayerGamesFromToken(@RequestHeader(value="Token") String tokenString) {
    return gameService.getCurrentPlayerGamesFromToken(tokenString);
  }

  @GetMapping("/games/{gameId}")
  public Game getCurrentPlayerGamesFromGameId(@RequestHeader(value="Token") String tokenString,
                                                     @PathVariable long gameId) throws RuntimeException {
      
      return gameService.getCurrentPlayerGameFromGameId(tokenString, gameId);

  }

  //----- Post Mappings -----//

  @PostMapping("/games/{gameId}")
  public String makeMoveInGame(@RequestHeader(value="Token") String tokenString,
                               @RequestBody ChessMove move,
                               @PathVariable long gameId) {
    try {
      gameService.makeMove(move, gameId, tokenString);
    }
    catch (Exception e) {
      System.out.println(">>>CAUGHT THE EXCEPTION THIS TIME");
    }
    return "Move made succesfully";
  }



}