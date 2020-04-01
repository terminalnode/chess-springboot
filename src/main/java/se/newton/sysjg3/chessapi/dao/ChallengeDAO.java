package se.newton.sysjg3.chessapi.dao;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;

public interface ChallengeDAO {
  public void create(Challenge challenge);
  public void delete(Challenge challenge);
  public Challenge getChallengeByParticipants(Player challenger, Player challenged);
  public List<Challenge> getChallengesByChallenger(Player challenger);
  public List<Challenge> getChallengesByChallenged(Player challenger);

}
