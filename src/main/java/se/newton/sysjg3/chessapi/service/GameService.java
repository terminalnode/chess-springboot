package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;

public interface GameService {

    public Game createNewGame(Challenge challenge);

}
