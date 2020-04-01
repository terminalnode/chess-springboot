package se.newton.sysjg3.chessapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.sysjg3.chessapi.dao.ChallengeDAO;
import se.newton.sysjg3.chessapi.dao.TokenDAO;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;
import se.newton.sysjg3.chessapi.rest.exceptions.ChallengeIdMismatchException;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChallengeServiceImplementation implements ChallengeService {
  private ChallengeDAO challengeDAO;
  private TokenDAO tokenDAO;
  private PlayerService playerServide;
  private GameService gameService;

  private long expirationTime;

  @Autowired
  public ChallengeServiceImplementation(ChallengeDAO challengeDAO, PlayerService playerService, GameService gameService, TokenDAO tokenDAO) {
    this.challengeDAO = challengeDAO;
    this.expirationTime = 24 * 3600 * 1000;

    this.gameService = gameService;
    this.playerServide = playerService;

    this.tokenDAO = tokenDAO;

  }

  @Override
  @Transactional
  public Challenge create(Player challenged, String tokenString) {


    Player challenger = tokenDAO.getPlayerFromTokenString(tokenString);
    long currentTime = System.currentTimeMillis();

    Challenge challenge = new Challenge(challenger, challenged);
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
    System.out.println("Challenge: " + challenge.getChallenger().getName() + " vs " + challenge.getChallenged().getName() + " created");
    return challenge;

  }

  @Override
  public List<Challenge> getChallengesByChallenger(Player challenger) {

    return challengeDAO.getChallengesByChallenger(challenger);

  }

  @Override
  public List<Challenge> getChallengesByChallenged(Player challenged) {



    return challengeDAO.getChallengesByChallenged(challenged);

  }

  @Override
  @Transactional
  public Game acceptChallenge(long challengeId, String tokenString) throws RuntimeException {
    Challenge challenge = challengeDAO.getChallengeById(challengeId);
    Player challenged = tokenDAO.getPlayerFromTokenString(tokenString);

    if (challenged.equals(challenge.getChallenged())) {
        challengeDAO.delete(challenge);
        return gameService.createNewGame(challenge);
    }
    else throw new ChallengeIdMismatchException("Error: Challenge ID does not match token ID", "ChallengeIdMismatchException");

  }

  @Override
  @Transactional
  public String declineChallenge(long challengeId, String tokenString) {
  Challenge challenge = challengeDAO.getChallengeById(challengeId);
  Player challenged = tokenDAO.getPlayerFromTokenString(tokenString);
  if (challenge == null) {
    System.out.println("CHALLENGE IS NULL");
  }
    if (challenged == null) {
      System.out.println("CHALLENGED IS NULL");
    }

    if (challenged.equals(challenge.getChallenged())) {
      challengeDAO.delete(challenge);
      return "Challenge declined.";
    }
    else throw new ChallengeIdMismatchException("Error: Challenge ID does not match token ID", "ChallengeIdMismatchException");
  }

}
