package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.helpers.ChessMove;

import java.util.List;

public interface GameService {
    public Game createNewGame(Challenge challenge) throws RuntimeException;
    public List<Game> getCurrentPlayerGamesFromToken(String tokenString) throws RuntimeException;
    public Game makeMove(ChessMove move, Game game, String tokenString) throws RuntimeException;
    Game makeMove(ChessMove move, long gameId, String tokenString) throws RuntimeException;
    public Game getCurrentPlayerGameFromGameId(String tokenString, long gameId) throws RuntimeException;


}
