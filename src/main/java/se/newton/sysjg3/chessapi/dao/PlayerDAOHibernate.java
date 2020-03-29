package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Player;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PlayerDAOHibernate implements PlayerDAO {
  private EntityManager entityManager;

  @Autowired
  public PlayerDAOHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void create(Player player) {
    Session session = entityManager.unwrap(Session.class);
    session.save(player);
  }

  @Override
  public List<Player> getAll() {
    Session session = entityManager.unwrap(Session.class);
    Query<Player> query = session.createQuery("from User", Player.class);
    return query.getResultList();
  }
}
