package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

public interface TokenService {
  public Token createTokenForPlayer(Player player);
  public Player getPlayerFromToken(Token token);
  public void extendToken(Token token);
  public boolean checkTokenExpiration(Token token);
  public Token getTokenFromTokenString(String tokenString);
  public Token getTokenFromTokenString(Token token);
}
