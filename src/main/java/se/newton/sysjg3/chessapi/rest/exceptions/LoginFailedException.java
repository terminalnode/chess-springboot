package se.newton.sysjg3.chessapi.rest.exceptions;

public class LoginFailedException extends AndroidChessException {

    public LoginFailedException(String message) {
        super(message, "LoginFailedException");
    }


}
