package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.sysjg3.chessapi.dao.ChallengeDAO;
import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.rest.exceptions.ChallengeAlreadyExistsException;
import se.newton.sysjg3.chessapi.rest.exceptions.ChallengeIdMismatchException;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChallengeServiceImplementation implements ChallengeService {
  private ChallengeDAO challengeDAO;
  private TokenService tokenService;
  private PlayerService playerService;
  private GameService gameService;
  private long expirationTime;

  @Autowired
  public ChallengeServiceImplementation(
      ChallengeDAO challengeDAO,
      PlayerService playerService,
      GameService gameService,
      TokenService tokenService) {
    this.challengeDAO = challengeDAO;
    this.expirationTime = 24 * 3600 * 1000;
    this.gameService = gameService;
    this.playerService = playerService;
    this.tokenService = tokenService;
  }

  @Override
  @Transactional
  public Challenge create(Player challenged, String token) {
    Player challenger = tokenService.getPlayerFromToken(token);
    challenged = playerService.getManagedPlayer(challenged);
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
      throw new ChallengeAlreadyExistsException(
          String.format(
              "%s already has a challenge against %s",
              challenger.getName(),
              challenged.getName())
      );
    }
    return challenge;

  }

  @Override
  public List<Challenge> getChallengesByChallenger(String token) {
    tokenService.checkTokenAndExtend(token);
    Player challenger = tokenService.getPlayerFromToken(token);
    return challengeDAO.getChallengesByChallenger(challenger);
  }

  @Override
  public List<Challenge> getChallengesByChallenged(String token) {
    tokenService.checkTokenAndExtend(token);
    Player challenged = tokenService.getPlayerFromToken(token);
    return challengeDAO.getChallengesByChallenged(challenged);
  }

  @Override
  @Transactional
  public Game acceptChallenge(long challengeId, String token) throws RuntimeException {
    tokenService.checkTokenAndExtend(token);
    Challenge challenge = challengeDAO.getChallengeById(challengeId);
    Player challenged = tokenService.getPlayerFromToken(token);

    if (challenged.equals(challenge.getChallenged())) {
        challengeDAO.delete(challenge);
        return gameService.createNewGame(challenge);

    } else {
      throw new ChallengeIdMismatchException("Challenge ID does not match token ID");
    }
  }

  @Override
  @Transactional
  public String declineChallenge(long challengeId, String token) {
    tokenService.checkTokenAndExtend(token);
    Challenge challenge = challengeDAO.getChallengeById(challengeId);
    Player challenged = tokenService.getPlayerFromToken(token);

    if (challenge == null) {
      System.out.println("CHALLENGE IS NULL");
    }

    if (challenged == null) {
      System.out.println("CHALLENGED IS NULL");
    }

    if (challenged.equals(challenge.getChallenged())) {
      challengeDAO.delete(challenge);
      return "Challenge declined.";

    } else {
      throw new ChallengeIdMismatchException("Error: Challenge ID does not match token ID");
    }
  }

}
