package se.newton.sysjg3.chessapi.dao;

import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;

public interface PlayerDAO {
  public void create(Player player);

  public List<Player> getAll();

  public Player getByName(String name);

  public Boolean verifyPassword(String name, String password);

}
