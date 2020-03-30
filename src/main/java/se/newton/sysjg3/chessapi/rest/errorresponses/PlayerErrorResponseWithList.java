package se.newton.sysjg3.chessapi.rest.errorresponses;

public class PlayerErrorResponseWithList extends PlayerErrorResponse {
  private String[] list;

  //----- Constructors -----//
  public PlayerErrorResponseWithList() {
    // empty no-arg constructor
  }

  public PlayerErrorResponseWithList(int status, String message, long timeStamp, String internalName, String[] list) {
    super(status, message, timeStamp, internalName);
    this.list = list;
  }

  //----- Getters and setters -----//
  public void setList(String[] list) {
    this.list = list;
  }

  public String[] getList() {
    return list;
  }
}
