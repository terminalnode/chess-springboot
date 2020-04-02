package se.newton.sysjg3.chessapi.rest.exceptions;

public class ChallengeAlreadyExistsException extends AndroidChessException {
  public ChallengeAlreadyExistsException(String message) {
    super(message, "ChallengeAlreadyExistsException");
  }
}
