package se.newton.sysjg3.chessapi.rest.exceptions;

public class PlayerCreateMissingFieldsException extends RuntimeException {
  public PlayerCreateMissingFieldsException(String message) {
    super(message);
  }
}
