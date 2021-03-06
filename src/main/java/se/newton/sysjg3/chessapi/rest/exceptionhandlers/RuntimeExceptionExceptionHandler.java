package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponse;

@ControllerAdvice
public class RuntimeExceptionExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(RuntimeException exc) {
    exc.printStackTrace();

    GenericErrorResponse error = new GenericErrorResponse();
    error.setMessage("Unknown error");
    error.setTimeStamp(System.currentTimeMillis());
    error.setInternalName("InternalServerErrorException");
    error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
