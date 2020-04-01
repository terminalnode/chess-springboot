package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.newton.sysjg3.chessapi.dao.PlayerDAO;
import se.newton.sysjg3.chessapi.entity.Player;

import java.util.List;

@Service
public class PlayerServiceImplementation implements PlayerService {
  private PlayerDAO playerDAO;

  @Autowired
  public PlayerServiceImplementation(PlayerDAO playerDAO) {
    this.playerDAO = playerDAO;
  }

  @Override
  @Transactional
  public void create(Player player) {
    playerDAO.create(player);
  }

  @Override
  public List<Player> getAll() {
    return playerDAO.getAll();
  }

  @Override
  public Player getByName(String name) {
    return playerDAO.getByName(name);
  }

  @Override
  public boolean checkUsernameAvailability(String name) {
    Player player = playerDAO.getByName(name);
    return player == null;
  }
}
