package se.newton.sysjg3.chessapi.rest.exceptions;

public class AlreadyFriendException extends AndroidChessException {
  public AlreadyFriendException(String message) {
    super(message, "AlreadyFriendException");
  }
}
