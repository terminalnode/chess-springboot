package se.newton.sysjg3.chessapi.rest.exceptions;

public class LoginNoSuchUserException extends AndroidChessException {
  public LoginNoSuchUserException(String message) {
    super(message, "LoginNoSuchUserException");
  }
}
