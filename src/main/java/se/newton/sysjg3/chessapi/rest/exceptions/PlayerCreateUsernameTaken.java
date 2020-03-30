package se.newton.sysjg3.chessapi.rest.exceptions;

public class PlayerCreateUsernameTaken extends AndroidChessException {
  public PlayerCreateUsernameTaken(String message) {
    super(message, "PlayerCreateUsernameTaken");
  }
}
