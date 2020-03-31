package se.newton.sysjg3.chessapi.rest.exceptions;

public abstract class AndroidChessExceptionWithList extends AndroidChessException {
  private String[] fields;

  public AndroidChessExceptionWithList(String message, String internalName, String[] fields) {
    super(message, internalName);
    this.fields = fields;
  }

  public String[] getFields() {
    return fields;
  }
}
