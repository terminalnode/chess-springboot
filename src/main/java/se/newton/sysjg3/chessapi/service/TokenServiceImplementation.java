package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.newton.sysjg3.chessapi.dao.PlayerDAO;
import se.newton.sysjg3.chessapi.dao.TokenDAO;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;
import se.newton.sysjg3.chessapi.rest.exceptions.LoginFailedToCreateTokenException;
import se.newton.sysjg3.chessapi.rest.exceptions.LoginIncorrectPasswordException;
import se.newton.sysjg3.chessapi.rest.exceptions.LoginNoSuchUserException;

@Service
public class TokenServiceImplementation implements TokenService {
  private PlayerDAO playerDAO;
  private TokenDAO tokenDAO;

  //----- Constructors -----//
  @Autowired
  public TokenServiceImplementation(PlayerDAO playerDAO, TokenDAO tokenDAO) {
    this.playerDAO = playerDAO;
    this.tokenDAO = tokenDAO;
  }

  //----- Implemented Methods -----//
  @Override
  public Token createTokenForPlayer(Player player) throws
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

  @Override
  public Player getPlayerFromToken(Token token) {
    return null;
  }

  @Override
  public void extendToken(Token token) {

  }

  @Override
  public boolean checkTokenExpiration(Token token) {
    return false;
  }

  @Override
  public Token getTokenFromTokenString(String tokenString) {
    return null;
  }

  @Override
  public Token getTokenFromTokenString(Token token) {
    return null;
  }

  @Override
  public void destroyToken(Token token) {
    tokenDAO.destroyToken(token);
  }
}
