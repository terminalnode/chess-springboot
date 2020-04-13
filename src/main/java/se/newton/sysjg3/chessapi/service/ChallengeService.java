package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Game;
import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;

public interface ChallengeService {
  public Challenge create(Player challenged, String tokenString) throws RuntimeException;
  public List<Challenge> getChallengesByChallenger(String tokenString) throws RuntimeException;
  public List<Challenge> getChallengesByChallenged(String tokenString) throws RuntimeException;
  public Game acceptChallenge(long challengeId, String tokenString) throws RuntimeException;
  public String declineChallenge(long challengeId, String tokenString) throws RuntimeException;
}
