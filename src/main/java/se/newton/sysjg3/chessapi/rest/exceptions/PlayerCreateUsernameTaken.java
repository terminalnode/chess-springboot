package se.newton.sysjg3.chessapi.rest.exceptions;

public class PlayerCreateUsernameTaken extends RuntimeException {
  public PlayerCreateUsernameTaken(String message) {
    super(message);
  }
}
