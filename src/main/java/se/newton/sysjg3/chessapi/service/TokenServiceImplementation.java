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
import se.newton.sysjg3.chessapi.rest.exceptions.TokenInvalidException;

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
  public Token createTokenForPlayer(Player player) throws RuntimeException {
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
  public Player getPlayerFromToken(Token token) throws RuntimeException {
    checkTokenExpiration(token);
    return tokenDAO.getPlayerFromToken(token);
  }

  @Override
  public Player getPlayerFromToken(String token) throws RuntimeException {
    Token actualToken = tokenDAO.getTokenFromTokenString(token);
    checkTokenExpiration(actualToken);
    return tokenDAO.getPlayerFromToken(actualToken);
  }

  @Override
  public Token extendToken(Token token) throws RuntimeException {
    Token actualToken = getTokenFromTokenString(token);
    return tokenDAO.extendToken(actualToken);
  }

  @Override
  public void checkTokenExpiration(Token token) throws RuntimeException {
    Token actualToken = getTokenFromTokenString(token);
    tokenDAO.checkTokenExpiration(actualToken);
    // Throws exception if token is invalid
  }

  @Override
  public Token checkTokenAndExtend(Token token) throws RuntimeException {
    Token actualToken = getTokenFromTokenString(token);
    checkTokenExpiration(actualToken);
    return extendToken(actualToken);
  }

  @Override
  public Token checkTokenAndExtend(String token) throws RuntimeException {
    Token actualToken = getTokenFromTokenString(token);
    checkTokenExpiration(actualToken);
    extendToken(actualToken);
    return actualToken;
  }

  @Override
  public Token getTokenFromTokenString(String token) throws RuntimeException {
    Token actualToken = tokenDAO.getTokenFromTokenString(token);

    if (actualToken == null) {
      throw new TokenInvalidException("The provided token is invalid");
    }

    return actualToken;
  }

  @Override
  public Token getTokenFromTokenString(Token token) throws RuntimeException {
    Token actualToken = tokenDAO.getTokenFromTokenString(token);

    if (actualToken == null) {
      throw new TokenInvalidException("The provided token is invalid");
    }

    return actualToken;
  }

  @Override
  public void destroyToken(Token token) throws RuntimeException {
    Token actualToken = getTokenFromTokenString(token);
    tokenDAO.destroyToken(actualToken);
  }

  @Override
  public void destroyToken(String token) throws RuntimeException {
    Token actualToken = getTokenFromTokenString(token);
    tokenDAO.destroyToken(actualToken);
  }
}
