package se.newton.sysjg3.chessapi.entity.chesspieces;

import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("pawn")
public class Pawn extends Piece {
  public Pawn() {
    // Default no-arg constructor
  }

  public Pawn(int id, int x, int y, boolean isWhite, Game game) {
    super(id, x, y, isWhite, game);
  }

  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    int dy = isWhite() ? 1 : -1;
    int yPlusOne = getY() + dy;
    int yPlusTwo = getY() + dy * 2;
    int diagonalRight = getX() + 1;
    int diagonalLeft = getX() - 1;

    // Check if pawn can make diagonal moves and/or if pawn is blocked
    boolean yPlusOneBlocked = false;
    boolean yPlusTwoBlocked = false;
    boolean pieceOnRight = false;
    boolean pieceOnLeft = false;

    for (Piece piece : pieces) {
      int otherX = piece.getX();
      int otherY = piece.getY();

      if (otherX == getX() && otherY == yPlusOne) {
        yPlusOneBlocked = true;
      } else if (otherX == getX() && otherY == yPlusTwo) {
        yPlusTwoBlocked = true;
      } else if (otherX == diagonalRight && otherY == yPlusOne) {
        pieceOnRight = true;
      } else if (otherX == diagonalLeft && otherY == yPlusOne) {
        pieceOnLeft = true;
      }
    }

    // Add moves to list
    if (!yPlusOneBlocked) {
      addMoveToList(moves, getX(), yPlusOne, pieces);

      if (!yPlusTwoBlocked && !isMoved()) {
        addMoveToList(moves, getX(), yPlusTwo, pieces);
      }
    }
    if (pieceOnLeft) {
      addMoveToList(moves, diagonalLeft, yPlusOne, pieces);
    }
    if (pieceOnRight) {
      addMoveToList(moves, diagonalRight, yPlusOne, pieces);
    }

    return moves;
  }

  @Override
  public Piece clonePiece() {
    return new Pawn(getInternalId(), getX(), getY(), isWhite(), getGame());
  }
}
