package se.newton.sysjg3.chessapi.rest.exceptions;

public class NotPartOfThisGameException extends AndroidChessException {
  public NotPartOfThisGameException(String message) {
    super(message, "NotPartOfThisGameException");
  }
}
