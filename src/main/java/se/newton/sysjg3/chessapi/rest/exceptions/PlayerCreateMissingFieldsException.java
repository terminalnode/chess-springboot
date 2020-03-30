package se.newton.sysjg3.chessapi.rest.exceptions;

public class PlayerCreateMissingFieldsException extends AndroidChessException {
  private String[] fields;

  public PlayerCreateMissingFieldsException(String message, String[] fields) {
    super(message, "PlayerCreateMissingFieldsException");
    this.fields = fields;
  }

  public String[] getFields() {
    return fields;
  }
}
