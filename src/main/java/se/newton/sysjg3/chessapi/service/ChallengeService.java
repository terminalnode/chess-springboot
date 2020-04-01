package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;

public interface ChallengeService {
  public Challenge create(Player challenged, String tokenString);
  public List<Challenge> getChallengesByChallenger(Player challenger);
  public List<Challenge> getChallengesByChallenged(Player challenged);
  public Game acceptChallenge(long challengeId, String tokenString);
}
