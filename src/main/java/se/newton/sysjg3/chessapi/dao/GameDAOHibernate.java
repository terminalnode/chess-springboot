package se.newton.sysjg3.chessapi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import se.newton.sysjg3.chessapi.helpers.ChessMove;

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
  public Game makeMove(ChessMove move, Game game) {
    Session session = entityManager.unwrap(Session.class);

    game = ManagedEntityHelper.getManaged(game, entityManager);
    game.movePiece(move);
    session.save(game);

    return game;
  }
}
