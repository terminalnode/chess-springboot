package se.newton.sysjg3.chessapi.dao;

import se.newton.sysjg3.chessapi.entity.Game;

public interface GameDAO {

    public Game create(Game game);
    public void delete(Game game);

}
