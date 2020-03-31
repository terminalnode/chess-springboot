package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.newton.sysjg3.chessapi.dao.PlayerDAO;
import se.newton.sysjg3.chessapi.dao.TokenDAO;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;
import se.newton.sysjg3.chessapi.rest.exceptions.LoginFailedToCreateTokenException;
import se.newton.sysjg3.chessapi.rest.exceptions.LoginIncorrectPasswordException;
import se.newton.sysjg3.chessapi.rest.exceptions.LoginNoSuchUserException;


@Service
public class LoginServiceImplementation implements LoginService {
  private PlayerDAO playerDAO;
  private TokenDAO tokenDAO;

  @Autowired
  public LoginServiceImplementation(PlayerDAO playerDAO, TokenDAO tokenDAO) {
    this.playerDAO = playerDAO;
    this.tokenDAO = tokenDAO;
  }

  @Override
  @Transactional
  public Token loginPlayer(Player player) throws
      LoginFailedToCreateTokenException,
      LoginIncorrectPasswordException,
      LoginNoSuchUserException {
    String name = player.getName();
    String password = player.getPassword();

    player = playerDAO.getByName(name);
    if (player == null) {
      throw new LoginNoSuchUserException("The requested user could not be found.");

    } else if (!player.getPassword().equals(password)) {
      throw new LoginIncorrectPasswordException("The password does not match that of the user.");
    }

    Token token = tokenDAO.createTokenForPlayer(player);
    if (token == null) {
      throw new LoginFailedToCreateTokenException("A token could not be created, please try again.");
    }

    return token;
  }
}
