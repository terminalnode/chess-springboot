package se.newton.sysjg3.chessapi.rest.exceptions;

public class NoSuchChallengeException extends AndroidChessException {

  public NoSuchChallengeException(String message) {
    super(message, "NoSuchChallengeException");
  }
}
