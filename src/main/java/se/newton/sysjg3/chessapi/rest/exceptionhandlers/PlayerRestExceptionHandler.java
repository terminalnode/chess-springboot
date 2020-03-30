package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.sysjg3.chessapi.rest.errorresponses.PlayerErrorResponse;
import se.newton.sysjg3.chessapi.rest.exceptions.PlayerCreateMissingFieldsException;
import se.newton.sysjg3.chessapi.rest.exceptions.PlayerCreateUsernameTaken;

@ControllerAdvice
public class PlayerRestExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<PlayerErrorResponse> handleException(PlayerCreateMissingFieldsException exc) {
    PlayerErrorResponse error = new PlayerErrorResponse();
    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<PlayerErrorResponse> handleException(PlayerCreateUsernameTaken exc) {
    PlayerErrorResponse error = new PlayerErrorResponse();
    error.setStatus(HttpStatus.BAD_REQUEST.value());
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
