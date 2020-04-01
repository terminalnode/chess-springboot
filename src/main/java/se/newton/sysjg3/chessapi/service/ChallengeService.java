package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;

public interface ChallengeService {
  public Challenge create(Challenge challenge);
  public List<Challenge> getChallengesByChallenger(Player challenger);
  public List<Challenge> getChallengesByChallenged(Player challenged);
}
