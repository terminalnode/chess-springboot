package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.sysjg3.chessapi.dao.GameDAO;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.helpers.ChessMove;
import se.newton.sysjg3.chessapi.rest.exceptions.IllegalMoveException;
import se.newton.sysjg3.chessapi.rest.exceptions.NoSuchGameException;
import se.newton.sysjg3.chessapi.rest.exceptions.NotPartOfThisGameException;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GameServiceImplementation implements GameService {
    private GameDAO gameDAO;
    private TokenService tokenService;

    @Autowired
    public GameServiceImplementation(GameDAO gameDAO, TokenService tokenService) {
        this.gameDAO = gameDAO;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public Game createNewGame(Challenge challenge) throws RuntimeException {
        Game game;

        if (Math.random() > 0.5) {
            game = new Game(challenge.getChallenger(), challenge.getChallenged());
        } else {
            game = new Game(challenge.getChallenged(), challenge.getChallenger());
        }

        gameDAO.create(game);
        return game;
    }

  @Override
  @Transactional
  public Game makeMove(ChessMove move, long gameId, String token) throws RuntimeException {
    return makeMove(move, gameDAO.getGameFromId(gameId), token);
  }

    @Override
    public Game makeMove(ChessMove move, Game game, String tokenString) throws RuntimeException  {
        Player movingPlayer = tokenService.getPlayerFromToken(tokenString);

        if (movingPlayer != game.getCurrentPlayer()) {
            throw new IllegalMoveException("It is not you turn!");
        }

        if (game.validateMove(move)) {
            gameDAO.makeMove(move, game);
        } else {
            throw new IllegalMoveException("The submitted move is not valid.");
        }
        return game;
    }

    @Override
    @Transactional
    public List<Game> getCurrentPlayerGamesFromToken(String tokenString) throws RuntimeException {
        Player currentPlayer = tokenService.getPlayerFromToken(tokenString);
        List<Game> gameList = gameDAO.getAllGamesForPlayer(currentPlayer);

        for (Game game:gameList) {
            setColorMessage(currentPlayer, game);
        }
        return gameList;
    }

    @Override
    public Game getCurrentPlayerGameFromGameId(String tokenString, long gameId) throws RuntimeException {
        Player currentPlayer = null;

        try {
             currentPlayer = tokenService.getPlayerFromToken(tokenString);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MOO");
            System.exit(1);
        }

        Game game = gameDAO.getGameFromId(gameId);
        if (game == null) {
            throw new NoSuchGameException("Unrecognized game id: " + gameId);
        }

        if (currentPlayer != game.getBlackPlayer() && currentPlayer != game.getWhitePlayer()) {
            throw new NotPartOfThisGameException("This player is not part of this game!");
        }

        setColorMessage(currentPlayer, game);
        return game;
    }

    private void setColorMessage(Player player, Game game) throws RuntimeException {
        if (player == game.getBlackPlayer()) {
            game.setGettingPlayerWhite(false);
        }
        else {
            game.setGettingPlayerWhite(true);
        }
    }
}
