package se.newton.sysjg3.chessapi.rest.exceptions;

public class NoSuchGameException extends AndroidChessException {
  public NoSuchGameException(String message) {
    super(message, "NoSuchGameException");
  }
}
