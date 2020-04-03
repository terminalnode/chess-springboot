package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponse;
import se.newton.sysjg3.chessapi.rest.exceptions.*;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GameRestExceptionHandler extends GenericRestExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(NoSuchGameException exc) {
    return responseEntityGenerator(exc, HttpStatus.NOT_FOUND);
  }
}