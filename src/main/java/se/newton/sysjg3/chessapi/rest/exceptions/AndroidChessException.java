package se.newton.sysjg3.chessapi.rest.exceptions;

public abstract class AndroidChessException extends RuntimeException {
  String internalName;

  public AndroidChessException(String message, String internalName) {
    super(message);
    this.internalName = internalName;
  }

  public String getInternalName() {
    return internalName;
  }
}
