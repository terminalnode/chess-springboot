package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponse;
import se.newton.sysjg3.chessapi.rest.exceptions.NoSuchTokenException;
import se.newton.sysjg3.chessapi.rest.exceptions.TokenInvalidException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenExceptionHandler extends GenericRestExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(TokenInvalidException exc) {
    return responseEntityGenerator(exc, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(NoSuchTokenException exc) {
    return responseEntityGenerator(exc, HttpStatus.NOT_FOUND);
  }
}
