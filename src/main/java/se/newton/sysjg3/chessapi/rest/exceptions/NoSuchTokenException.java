package se.newton.sysjg3.chessapi.rest.exceptions;

public class NoSuchTokenException extends AndroidChessException {
  public NoSuchTokenException(String message) {
    super(message, "NoSuchTokenException");
  }
}
