package se.newton.sysjg3.chessapi.rest.exceptions;

public class CantAddSelfAsFriendException extends AndroidChessException {
  public CantAddSelfAsFriendException(String message) {
    super(message, "CantAddSelfAsFriendException");
  }
}
