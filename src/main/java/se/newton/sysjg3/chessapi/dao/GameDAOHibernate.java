package se.newton.sysjg3.chessapi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.chesspieces.Piece;
import se.newton.sysjg3.chessapi.helpers.ChessMove;

import java.util.List;

@Repository
public class GameDAOHibernate implements GameDAO {
  private EntityManager entityManager;

  //----- Constructor -----//
  @Autowired
  public GameDAOHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Game create(Game game) {

    Session session = entityManager.unwrap(Session.class);
    session.save(game);
    return game;
  }

  @Override
  public void delete(Game game) {
    Session session = entityManager.unwrap(Session.class);
    session.delete(game);
  }

  @Override
  @Transactional
  public Game makeMove(ChessMove move, Game game) {
    Session session = entityManager.unwrap(Session.class);
    game = ManagedEntityHelper.getManaged(game, entityManager);
    game.populatePieceMap();

    // If there is a piece at target location, remove it. Then move.
    Piece takenPiece = game.getPieceAtCoordinates(move.getDestination()[0], move.getDestination()[1]);
    game.movePiece(move);
    if (game.checkForCheck() && game.checkForCheckMate()) {
        game.setFinished(true);
    }

    // Remove the piece from the board and the session, then update the board.
    if (takenPiece != null) {
      game.removePiece(takenPiece);
      session.delete(takenPiece);
    }
    session.update(game);
    return game;
  }

  @Override
  public List<Game> getAllGamesForPlayer(Player player) {

    Session session = entityManager.unwrap(Session.class);
    Query<Game> blackQuery = session.createQuery("FROM Game g WHERE g.blackPlayer = :player", Game.class);
    Query<Game> whiteQuery = session.createQuery("FROM Game g WHERE g.whitePlayer = :player", Game.class);
    blackQuery.setParameter("player", player);
    whiteQuery.setParameter("player", player);
    List<Game> fullGameList = blackQuery.getResultList();
    fullGameList.addAll(whiteQuery.getResultList());
    return fullGameList;

  }


  public Game getGameFromId(long gameId) {
    Session session = entityManager.unwrap(Session.class);
      return session.get(Game.class, gameId);
  }

}
