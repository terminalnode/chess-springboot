package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

public interface TokenService {
  public Token createTokenForPlayer(Player player) throws RuntimeException;
  public Player getPlayerFromToken(Token token) throws RuntimeException;
  public Player getPlayerFromToken(String token) throws RuntimeException;
  public void extendToken(Token token) throws RuntimeException;
  public void checkTokenExpiration(Token token) throws RuntimeException;
  public void checkTokenAndExtend(Token token) throws RuntimeException;
  public void checkTokenAndExtend(String token) throws RuntimeException;
  public Token getTokenFromTokenString(String tokenString) throws RuntimeException;
  public Token getTokenFromTokenString(Token token) throws RuntimeException;
  public void destroyToken(Token token) throws RuntimeException;
  public void destroyToken(String tokenString) throws RuntimeException;
}
