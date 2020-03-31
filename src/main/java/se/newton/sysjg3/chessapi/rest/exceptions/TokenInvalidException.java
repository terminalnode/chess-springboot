package se.newton.sysjg3.chessapi.rest.exceptions;

public class TokenInvalidException extends AndroidChessException {
  public TokenInvalidException(String message) {
    super(message, "TokenInvalidException");
  }
}
