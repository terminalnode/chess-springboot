package se.newton.sysjg3.chessapi.dao;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;
import java.util.Set;

public interface PlayerDAO {
  public Player create(Player player);
  public List<Player> getAll();
  public Player getByName(String name);
  public Player getById(long id);
  public Player get(Player player);
  public Set<Player> getFriends(Player player);
  public Player addFriend(Player player, Player newFriend);
}
