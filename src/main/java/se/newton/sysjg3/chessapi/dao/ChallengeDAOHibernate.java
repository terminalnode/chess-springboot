package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;
import se.newton.sysjg3.chessapi.rest.exceptions.NoSuchChallengeException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ChallengeDAOHibernate implements ChallengeDAO {
  private EntityManager entityManager;
  private long expirationTime;


  @Autowired
  public ChallengeDAOHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.expirationTime = 24 * 3600 * 1000;
  }

  @Transactional
  @Override
  public void create(Challenge challenge) {
    challenge.setCreatedAt(System.currentTimeMillis());
    Session session = entityManager.unwrap(Session.class);
    session.save(challenge);
  }

  @Transactional
  @Override
  public void delete(Challenge challenge) {
    Session session = entityManager.unwrap(Session.class);
    session.delete(challenge);
  }

  @Override
  public Challenge getChallengeByParticipants(Player challenger, Player challenged) {
    Session session = entityManager.unwrap(Session.class);
    try {
    return session.byNaturalId(Challenge.class)
        .using("challenger", challenger)
        .using("challenged", challenged)
        .load();
    }
    catch (Exception e) {
      System.out.println(">>>THE PROGRAM GOES HERE!");
        e.printStackTrace();
    }
    return null;
  }

    @Override
    public Challenge getChallengeById(long challengeId) {
      try {
        Session session = entityManager.unwrap(Session.class);
        return session.getReference(Challenge.class, challengeId);
      } catch (EntityNotFoundException e) {
        throw new NoSuchChallengeException("That challenge does not exist.");
      }
    }

  @Override
  public List<Challenge> getChallengesByChallenger(Player challenger) {
    Session session = entityManager.unwrap(Session.class);
    Query<Challenge> query = session.createQuery("FROM Challenge c WHERE c.challenger = :player", Challenge.class);
    query.setParameter("player", challenger);
    return query.getResultList();

  }

  @Override
  public List<Challenge> getChallengesByChallenged(Player challenged){
    Session session = entityManager.unwrap(Session.class);
    Query<Challenge> query = session.createQuery("FROM Challenge c WHERE c.challenged = :player", Challenge.class);
    query.setParameter("player", challenged);
    return query.getResultList();
  }
}
