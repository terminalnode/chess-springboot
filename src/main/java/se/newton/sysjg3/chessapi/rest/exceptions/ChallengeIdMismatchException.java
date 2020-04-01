package se.newton.sysjg3.chessapi.rest.exceptions;

public class ChallengeIdMismatchException extends AndroidChessException {

    public ChallengeIdMismatchException(String message, String internalName) {
        super(message, internalName);
    }
}
