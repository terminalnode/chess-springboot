package se.newton.sysjg3.chessapi.rest.exceptions;

public class PlayerCreateMissingFieldsException extends AndroidChessExceptionWithList {
  public PlayerCreateMissingFieldsException(String message, String[] fields) {
    super(message, "PlayerCreateMissingFieldsException", fields);
  }
}
