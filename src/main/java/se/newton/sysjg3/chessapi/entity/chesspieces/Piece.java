package se.newton.sysjg3.chessapi.entity.chesspieces;

import java.util.List;

public abstract class Piece {
  protected int id, x, y;
  protected boolean isWhite, moved;


  //----- Constructors -----//
  public Piece() {
    // Empty no-arg constructor
  }

  public Piece(int id, int x, int y, boolean isWhite) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.isWhite = isWhite;
    moved = false;
  }

  //----- Abstract methods -----//
  public abstract List<int[]> getMoves(List<Piece> pieces);

  //----- Methods -----//
  public void move(int x, int y) {
    this.x = x;
    this.y = y;
  }

  //----- Setters -----//
  public void setId(int id) {
    this.id = id;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setWhite(boolean white) {
    isWhite = white;
  }

  public void setMoved(boolean moved) {
    this.moved = moved;
  }

  //----- Getters -----//
  public int getId() {
    return id;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isWhite() {
    return isWhite;
  }

  public boolean isMoved() {
    return moved;
  }
}
