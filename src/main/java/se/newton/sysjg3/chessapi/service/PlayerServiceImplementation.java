package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.newton.sysjg3.chessapi.dao.PlayerDAO;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.rest.exceptions.AddFriendException;
import se.newton.sysjg3.chessapi.rest.exceptions.AlreadyFriendException;
import se.newton.sysjg3.chessapi.rest.exceptions.CantAddSelfAsFriendException;

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

    if (player == null) {
      throw new AddFriendException("I don't know who you are.");
    }

    if (player.getFriends().contains(actualFriend)) {
      throw new AlreadyFriendException(
          String.format(
              "You and %s are already buddies.",
              actualFriend.getName())
      );
    }

    if (actualFriend == null) {
      throw new AddFriendException("I don't know who you're friend is.");
    }

    if (player.equals(actualFriend)) {
      throw new CantAddSelfAsFriendException("You can't add yourself as a friend.");
    } else {
      Player result = playerDAO.addFriend(player, actualFriend);
      if (result == null) {
        throw new AddFriendException("Failed to add friend.");
      }
      return result;
    }
  }

  @Override
  public List<Player> searchFriendByString(String searchString, String tokenString) throws RuntimeException {

    Player searchingPlayer = tokenService.getPlayerFromToken(tokenString);

    Set<Player> searchingPlayersFriends = searchingPlayer.getFriends();

    List<Player> potentialNewFriends = playerDAO.searchPlayersByString(searchString);

    for (Player oldFriend : searchingPlayersFriends) {
      if (potentialNewFriends.contains(oldFriend)) {
        potentialNewFriends.remove(oldFriend);
      }
    }
    if (potentialNewFriends.contains(searchingPlayer)) {
        potentialNewFriends.remove(searchingPlayer);
    }
    return potentialNewFriends;
  }

  @Override
  public Player getManagedPlayer(Player player) throws RuntimeException {
    return playerDAO.get(player);
  }
}
