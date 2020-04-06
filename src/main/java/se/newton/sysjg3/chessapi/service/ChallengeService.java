package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;

public interface ChallengeService {
  public Challenge create(Player challenged, String tokenString);
  public List<Challenge> getChallengesByChallenger(String tokenString);
  public List<Challenge> getChallengesByChallenged(String tokenString);
  public Game acceptChallenge(long challengeId, String tokenString);
  public String declineChallenge(long challengeId, String tokenString);
}
