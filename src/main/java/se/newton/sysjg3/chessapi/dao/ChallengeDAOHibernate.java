package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;

import javax.persistence.EntityManager;
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


  @Override
  public List<Challenge> getChallengesByChallenger(Player challenger) {
    Session session = entityManager.unwrap(Session.class);
    Query<Challenge> query = session.createQuery("from Challenges where Challenges.challenger_id= :challenger_id", Challenge.class);
    query.setParameter("challenger_id", challenger.getId());
    return query.getResultList();

  }

  @Override
  public List<Challenge> getChallengesByChallenged(Player challenged){
    Session session = entityManager.unwrap(Session.class);
    Query<Challenge> query = session.createQuery("from Challenges where Challenges.challenged_id = :challenged_id", Challenge.class);
    query.setParameter("challenged_id", challenged.getId());
    return query.getResultList();

  }




}
