package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.sysjg3.chessapi.dao.ChallengeDAO;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;

@Service
public class ChallengeServiceImplementation implements ChallengeService {
  private ChallengeDAO challengeDAO;
  private long expirationTime;

  @Autowired
  public ChallengeServiceImplementation(ChallengeDAO challengeDAO) {
    this.challengeDAO = challengeDAO;
    this.expirationTime = 24 * 3600 * 1000;
  }

  @Override
  public Challenge create(Challenge challenge) {
    Player challenger = challenge.getChallenger();
    Player challenged = challenge.getChallenged();
    long currentTime = System.currentTimeMillis();
    Challenge oldChallenge = challengeDAO.getChallengeByParticipants(challenger, challenged);

    if (oldChallenge == null) {
      // No previous challenge exists, create this one.
      challengeDAO.create(challenge);

    } else if (currentTime - oldChallenge.getCreatedAt() > expirationTime) {
      // Challenge has expired, delete it and create a new one.
      challengeDAO.delete(oldChallenge);
      challenge.setCreatedAt(currentTime);
      challengeDAO.create(challenge);

    } else {
      // Challenge already exists, no new one will be created.
      challenge = null;
    }

    return challenge;
  }
}
