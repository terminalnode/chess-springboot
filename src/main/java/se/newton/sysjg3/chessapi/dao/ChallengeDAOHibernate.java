package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;

import javax.persistence.EntityManager;

@Repository
public class ChallengeDAOHibernate implements ChallengeDAO {
  private EntityManager entityManager;
  private long expirationTime;

  @Autowired
  public ChallengeDAOHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.expirationTime = 24 * 3600 * 1000;
  }

  @Override
  public void create(Challenge challenge) {
    challenge.setCreatedAt(System.currentTimeMillis());
    Session session = entityManager.unwrap(Session.class);
    session.save(challenge);
  }

  @Override
  public void delete(Challenge challenge) {
    Session session = entityManager.unwrap(Session.class);
    session.delete(challenge);
  }

  @Override
  public Challenge getChallengeByParticipants(Player challenger, Player challenged) {
    Session session = entityManager.unwrap(Session.class);
    return session.byNaturalId(Challenge.class)
        .using("challenger", challenger)
        .using("challenged", challenged)
        .load();
  }
}
