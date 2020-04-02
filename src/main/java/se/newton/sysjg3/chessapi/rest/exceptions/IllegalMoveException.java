package se.newton.sysjg3.chessapi.rest.exceptions;

public class IllegalMoveException extends AndroidChessException {

    public IllegalMoveException(String message) {
      super(message, "IllegalMoveException");
    }
}
