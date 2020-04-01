package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponse;
import se.newton.sysjg3.chessapi.rest.exceptions.TokenInvalidException;

@ControllerAdvice
public class TokenExceptionHandler extends GenericRestExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(TokenInvalidException exc) {
    return responseEntityGenerator(exc, HttpStatus.UNAUTHORIZED);
  }
}
