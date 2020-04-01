package se.newton.sysjg3.chessapi.rest.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponse;
import se.newton.sysjg3.chessapi.rest.errorresponses.GenericErrorResponseWithList;
import se.newton.sysjg3.chessapi.rest.exceptions.*;

@ControllerAdvice
public class PlayerRestExceptionHandler extends GenericRestExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(PlayerCreateUsernameTaken exc) {
    return responseEntityGenerator(exc, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponseWithList> handleException(PlayerCreateMissingFieldsException exc) {
    return responseEntityGeneratorWithList(exc, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(AlreadyFriendException exc) {
    return responseEntityGenerator(exc, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(CantAddSelfAsFriendException exc) {
    return responseEntityGenerator(exc, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  public ResponseEntity<GenericErrorResponse> handleException(AddFriendException exc) {
    return responseEntityGenerator(exc, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
