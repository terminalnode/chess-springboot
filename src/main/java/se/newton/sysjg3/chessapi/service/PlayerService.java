package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;
import java.util.Set;

public interface PlayerService {
  public void create(Player player) throws RuntimeException;
  public List<Player> getAll() throws RuntimeException;
  public Player getByName(String name) throws RuntimeException;
  public boolean checkUsernameAvailability(String name) throws RuntimeException;
  public Set<Player> getFriends(String token) throws RuntimeException;
  public Player addFriend(String token, Player newFriend) throws RuntimeException;
}
