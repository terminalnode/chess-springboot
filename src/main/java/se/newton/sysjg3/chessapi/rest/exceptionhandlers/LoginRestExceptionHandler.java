package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponse;
import se.newton.sysjg3.chessapi.rest.exceptions.*;

@ControllerAdvice
public class LoginRestExceptionHandler extends GenericRestExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(LoginFailedException exc) {
    return responseEntityGenerator(exc, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(LoginFailedToCreateTokenException exc) {
    return responseEntityGenerator(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(LoginIncorrectPasswordException exc) {
    return responseEntityGenerator(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(LoginNoSuchUserException exc) {
    return responseEntityGenerator(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
