package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.helpers.ChessMove;



public interface GameDAO {

    public Game create(Game game);
    public void delete(Game game);
    public Game makeMove(ChessMove move, Game game);



}
