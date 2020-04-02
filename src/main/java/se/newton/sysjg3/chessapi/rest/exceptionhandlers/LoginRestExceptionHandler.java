package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponse;
import se.newton.sysjg3.chessapi.rest.exceptions.*;

import javax.annotation.Priority;

@ControllerAdvice
@Priority(1)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoginRestExceptionHandler extends GenericRestExceptionHandler {
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
