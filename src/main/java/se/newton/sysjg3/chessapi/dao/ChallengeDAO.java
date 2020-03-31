package se.newton.sysjg3.chessapi.dao;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;

public interface ChallengeDAO {
  public void create(Challenge challenge);
  public void delete(Challenge challenge);
  public Challenge getChallengeByParticipants(Player challenger, Player challenged);
}
