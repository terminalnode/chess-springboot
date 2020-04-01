package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;

public interface PlayerService {
  public void create(Player player);
  public List<Player> getAll();
  public Player getByName(String name);
  public boolean checkUsernameAvailability(String name);
}
