package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.sysjg3.chessapi.dao.GameDAO;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;

import javax.transaction.Transactional;

@Service
public class GameServiceImplementation implements GameService {

    private GameDAO gameDAO;

    @Autowired
    public GameServiceImplementation(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
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



}
