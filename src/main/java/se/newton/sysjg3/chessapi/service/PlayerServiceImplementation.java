package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.newton.sysjg3.chessapi.dao.PlayerDAO;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.rest.exceptions.AndroidChessException;

import java.util.List;
import java.util.Set;

@Service
public class PlayerServiceImplementation implements PlayerService {
  private PlayerDAO playerDAO;
  private TokenService tokenService;

  @Autowired
  public PlayerServiceImplementation(PlayerDAO playerDAO, TokenService tokenService) {
    this.playerDAO = playerDAO;
    this.tokenService = tokenService;
  }

  @Override
  @Transactional
  public void create(Player player) throws RuntimeException {
    playerDAO.create(player);
  }

  @Override
  public List<Player> getAll() throws RuntimeException {
    return playerDAO.getAll();
  }

  @Override
  public Player getByName(String name) throws RuntimeException {
    return playerDAO.getByName(name);
  }

  @Override
  public boolean checkUsernameAvailability(String name) throws RuntimeException {
    Player player = playerDAO.getByName(name);
    return player == null;
  }

  @Override
  public Set<Player> getFriends(String token) throws RuntimeException {
    tokenService.checkTokenAndExtend(token);
    Player player = tokenService.getPlayerFromToken(token);
    return player.getFriends();
  }

  @Override
  public Player addFriend(String token, Player newFriend) throws RuntimeException {
    tokenService.checkTokenAndExtend(token);
    Player player = tokenService.getPlayerFromToken(token);
    Player actualFriend = playerDAO.get(newFriend);
    return playerDAO.addFriend(player, actualFriend);
  }
}
