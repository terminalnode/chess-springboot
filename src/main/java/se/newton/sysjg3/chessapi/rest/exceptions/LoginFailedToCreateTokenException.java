package se.newton.sysjg3.chessapi.rest.exceptions;

public class LoginFailedToCreateTokenException extends AndroidChessException {
  public LoginFailedToCreateTokenException(String message) {
    super(message, "LoginFailedToCreateTokenException");
  }
}
