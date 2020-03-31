package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponse;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponseWithList;
import se.newton.sysjg3.chessapi.rest.exceptions.AndroidChessException;
import se.newton.sysjg3.chessapi.rest.exceptions.AndroidChessExceptionWithList;

public abstract class GenericRestExceptionHandler {
  protected ResponseEntity<GenericErrorResponse> responseEntityGenerator(
      AndroidChessException exc,
      HttpStatus status) {

    GenericErrorResponse error = new GenericErrorResponse();
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());
    error.setInternalName(exc.getInternalName());
    error.setStatus(status.value());
    return new ResponseEntity<>(error, status);
  }
  protected ResponseEntity<GenericErrorResponseWithList> responseEntityGeneratorWithList(
      AndroidChessExceptionWithList exc,
      HttpStatus status) {

    GenericErrorResponseWithList error = new GenericErrorResponseWithList();
    error.setMessage(exc.getMessage());
    error.setTimeStamp(System.currentTimeMillis());
    error.setInternalName(exc.getInternalName());
    error.setStatus(status.value());
    error.setList(exc.getFields());
    return new ResponseEntity<>(error, status);
  }
}
