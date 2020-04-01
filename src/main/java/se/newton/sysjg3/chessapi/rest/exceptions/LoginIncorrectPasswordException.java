package se.newton.sysjg3.chessapi.rest.exceptions;

public class LoginIncorrectPasswordException extends AndroidChessException {
  public LoginIncorrectPasswordException(String message) {
    super(message, "LoginIncorrectPasswordException");
  }
}
