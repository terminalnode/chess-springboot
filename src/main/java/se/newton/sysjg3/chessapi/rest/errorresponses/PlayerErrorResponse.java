package se.newton.sysjg3.chessapi.rest.errorresponses;

public class PlayerErrorResponse {
  private int status;
  private String message;
  private long timeStamp;

  //----- Constructors -----//
  public PlayerErrorResponse() {
    // empty no-arg constructor
  }

  public PlayerErrorResponse(int status, String message, long timeStamp) {
    this.status = status;
    this.message = message;
    this.timeStamp = timeStamp;
  }

  //----- Getters and setters -----//
  public void setStatus(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public long getTimeStamp() {
    return timeStamp;
  }
}
