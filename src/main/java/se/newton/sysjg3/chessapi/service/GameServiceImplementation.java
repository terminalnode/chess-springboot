package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.sysjg3.chessapi.dao.GameDAO;
import se.newton.sysjg3.chessapi.dao.GameDAOHibernate;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.helpers.ChessMove;
import se.newton.sysjg3.chessapi.rest.exceptions.IllegalMoveException;

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
    public Game createNewGame(Challenge challenge) {
        Game game;
        //TODO Add proper 50/50 random here
        if (true) {
            game = new Game(challenge.getChallenger(), challenge.getChallenged());
        }
        else {
            game = new Game(challenge.getChallenged(), challenge.getChallenger());
        }

        gameDAO.create(game);

        return game;
    }

    @Override
    @Transactional
    public Game makeMove(ChessMove move, Game game, String tokenString) {

        Player movingPlayer = tokenService.getPlayerFromToken(tokenString);
        if (movingPlayer != game.getCurrentPlayer()) {
            throw new IllegalMoveException("It is not you turn!");
        }
        if (game.validateMove(move)) {
            gameDAO.makeMove(move, game);
        }
        return game;
    }

    @Override
    public Game makeMove(ChessMove move, long gameId, String tokenString) {
        Game game = gameDAO.getGameFromId(gameId);
        return makeMove(move, game, tokenString);
    }

    public List<Game> getCurrentPlayerGamesFromToken(String tokenString) {

        Player currentPlayer = tokenService.getPlayerFromToken(tokenString);
        return gameDAO.getAllGamesForPlayer(currentPlayer);
    }




}
