package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.helpers.ChessMove;

import java.util.List;

public interface GameService {

    public Game createNewGame(Challenge challenge);
    public List<Game> getCurrentPlayerGamesFromToken(String tokenString);
    public Game makeMove(ChessMove move, Game game, String tokenString);
    Game makeMove(ChessMove move, long gameId, String tokenString);

}
